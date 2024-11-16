package com.wcd.farm.presentation.viewmodel

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gun0912.tedpermission.coroutine.TedPermission
import com.wcd.farm.data.repository.DiseaseRepository
import com.wcd.farm.presentation.intent.DiseaseViewIntent
import com.wcd.farm.presentation.state.DiseaseViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class DiseaseViewModel @AssistedInject constructor(
    @Assisted initialState: DiseaseViewState,
    private val repository: DiseaseRepository
) :
    MavericksViewModel<DiseaseViewState>(initialState) {
    private val diseaseViewIntent = Channel<DiseaseViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<DiseaseViewModel, DiseaseViewState> {
        override fun create(state: DiseaseViewState): DiseaseViewModel
    }

    companion object :
        MavericksViewModelFactory<DiseaseViewModel, DiseaseViewState> by hiltMavericksViewModelFactory()

    private val cameraProvider = repository.cameraProvider
    private val imageCapture = repository.imageCapture

    val bitmap = repository.bitmap
    val diseaseDetect = repository.diseaseDetect

    init {
        handleIntent()
        repository.setImageCapture(
            ImageCapture.Builder().setTargetResolution(Size(720, 1280)).build()
        )
    }

    fun sendIntent(intent: DiseaseViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        diseaseViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            diseaseViewIntent.consumeAsFlow().collect { intent ->
                Log.e("TEST", "intent: $intent")
                when (intent) {
                    DiseaseViewIntent.ShowDiseaseView -> setState { copy(showDiseaseView = true) }
                    DiseaseViewIntent.HideDiseaseView -> {
                        setState { DiseaseViewState() }
                        stopCamera()
                    }

                    DiseaseViewIntent.ShowPreviewCaptureView -> {
                        setState { copy(viewState = 0) }
                    }

                    DiseaseViewIntent.ShowCaptureImageView -> {
                        setState { copy(viewState = 1) }
                    }

                    DiseaseViewIntent.ShowDiseaseDetectionResult -> {
                        setState { copy(showDiseaseDetectResult = true, onDiseaseDetect = true) }
                    }

                    DiseaseViewIntent.ShowDiseaseDetected -> {
                        setState { copy(onDiseaseDetect = false, isPlantDisease = true) }
                    }

                    DiseaseViewIntent.ShowDiseaseNotDetected -> {
                        setState { copy(onDiseaseDetect = false, isPlantDisease = false) }
                    }
                }
            }
        }
    }

    fun requestPermission() {
        CoroutineScope(Dispatchers.IO).launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .check()
        }
    }

    fun startCamera(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        cacheDir: File
    ) {
        repository.setCacheDir(cacheDir)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)

        cameraProviderFuture.addListener({
            repository.setCameraProvider(cameraProviderFuture.get())
            bindCamera(previewView, lifecycleOwner)
        }, ContextCompat.getMainExecutor(previewView.context))
    }

    private fun bindCamera(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        val preview = Preview.Builder().build().apply {
            surfaceProvider = previewView.surfaceProvider
        }
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.value?.unbindAll()
            cameraProvider.value?.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis,
                imageCapture.value
            )
        } catch (exc: Exception) {
            Log.e("QRCodeScanner", "Use case binding failed", exc)
        }
    }

    fun showPreviewCaptureView() {
        sendIntent(DiseaseViewIntent.ShowPreviewCaptureView)
    }

    fun takePhoto(context: Context) {
        val mImageCapture = imageCapture.value ?: return
        val externalDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val newPhotoFile = File(externalDir, "newImage.jpg")
        if (newPhotoFile.exists()) {
            newPhotoFile.delete()
        }
        repository.setPhotoFile(newPhotoFile)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            newPhotoFile
        ).build()

        mImageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    stopCamera()
                    try {
                        CoroutineScope(Dispatchers.Default).launch {
                            val bitmap = BitmapFactory.decodeFile(outputFileResults.savedUri!!.path)

                            repository.setPreviewImage(bitmap)
                            sendIntent(DiseaseViewIntent.ShowCaptureImageView)
                        }

                    } catch (exception: Exception) {
                        Log.e("TEST", exception.message!!)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("TEST", "사진 촬영 실패 ${exception.message}")
                }
            }
        )


    }

    fun stopCamera() {
        cameraProvider.value?.unbindAll()
    }

    fun requestDiseaseDetection() {

        repository.requestPlantDiseaseDetection(
            diseaseDetected = { isDetected ->
                Log.e("TEST", "isDetected: $isDetected")
                if (isDetected) {
                    sendIntent(DiseaseViewIntent.ShowDiseaseDetected)
                } else {
                    sendIntent(DiseaseViewIntent.ShowDiseaseNotDetected)
                }
            })
    }

    fun closeDiseaseView() {
        sendIntent(DiseaseViewIntent.HideDiseaseView)
    }
}