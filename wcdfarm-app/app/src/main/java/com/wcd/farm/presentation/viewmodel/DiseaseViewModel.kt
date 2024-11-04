package com.wcd.farm.presentation.viewmodel

import android.Manifest
import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.gun0912.tedpermission.coroutine.TedPermission
import com.wcd.farm.presentation.intent.DiseaseViewIntent
import com.wcd.farm.presentation.state.DiseaseViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DiseaseViewModel @AssistedInject constructor(@Assisted initialState: DiseaseViewState) :
    MavericksViewModel<DiseaseViewState>(initialState) {
    private val diseaseViewIntent = Channel<DiseaseViewIntent>()

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<DiseaseViewModel, DiseaseViewState> {
        override fun create(state: DiseaseViewState): DiseaseViewModel
    }

    companion object :
        MavericksViewModelFactory<DiseaseViewModel, DiseaseViewState> by hiltMavericksViewModelFactory()

    private lateinit
    var cameraProvider: ProcessCameraProvider

    init {
        handleIntent()
    }

    fun sendIntent(intent: DiseaseViewIntent) = viewModelScope.launch(Dispatchers.Main) {
        diseaseViewIntent.send(intent)
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Main) {
            diseaseViewIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    DiseaseViewIntent.ShowDiseaseView -> setState { copy(showDiseaseView = true) }
                    DiseaseViewIntent.HideDiseaseView -> setState { copy(showDiseaseView = false) }
                }
            }
        }
    }

    fun requestPermission() {
        CoroutineScope(Dispatchers.IO).launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(
                        Manifest.permission.CAMERA
                    )
                    .check()
        }
    }

    fun startCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCamera(cameraProvider, previewView, lifecycleOwner)
        }, ContextCompat.getMainExecutor(previewView.context))
    }

    private fun bindCamera(
        cameraProvider: ProcessCameraProvider,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)

        } catch (exc: Exception) {
            Log.e("QRCodeScanner", "Use case binding failed", exc)
        }
    }

    fun stopCamera() {
        cameraProvider.unbindAll()
    }
}