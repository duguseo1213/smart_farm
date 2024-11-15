package com.wcd.farm.data.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import com.wcd.farm.data.model.PlantDiseaseDTO
import com.wcd.farm.data.remote.GardenApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class DiseaseRepository @Inject constructor(private val gardenApi: GardenApi) {
    private val _cameraProvider = MutableStateFlow<ProcessCameraProvider?>(null)
    val cameraProvider = _cameraProvider.asStateFlow()

    private val _photoFile = MutableStateFlow<File?>(null)
    val photoFile = _photoFile.asStateFlow()

    private val _cacheDir = MutableStateFlow<File?>(null)
    val cacheDir = _cacheDir.asStateFlow()

    private val _imageCapture = MutableStateFlow<ImageCapture?>(null)
    val imageCapture = _imageCapture.asStateFlow()

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    private val _diseaseDetect = MutableStateFlow<PlantDiseaseDTO?>(null)
    val diseaseDetect = _diseaseDetect.asStateFlow()

    fun setCameraProvider(cameraProvider: ProcessCameraProvider) {
        _cameraProvider.value = cameraProvider
    }

    fun setCacheDir(cacheDir: File) {
        _cacheDir.value = cacheDir
    }

    fun setPhotoFile(photoFile: File) {
        _photoFile.value = photoFile
    }

    fun setImageCapture(imageCapture: ImageCapture) {
        _imageCapture.value = imageCapture
    }

    fun setPreviewImage(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun requestPlantDiseaseDetection(isDone: () -> Unit, diseaseDetected: (diseaseDetected: Boolean) -> Unit) {

        val imageString = _bitmap.value?.let { getBase64String(it) }
        if (imageString != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = gardenApi.postDiseaseDetection(imageString)
                isDone()
                if(response.isSuccessful) {
                    Log.e("TEST", response.body()?.data.toString())
                    val result = response.body()?.data

                    if (result != null) {
                        diseaseDetected(result.diseased)
                        _diseaseDetect.value = result
                    }
                }
            }

        }
    }

    private fun getBase64String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val base64String = android.util.Base64.encodeToString(imageBytes, android.util.Base64.NO_WRAP)
        return base64String
    }
}