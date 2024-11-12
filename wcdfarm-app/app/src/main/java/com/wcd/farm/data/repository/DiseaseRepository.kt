package com.wcd.farm.data.repository

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import com.wcd.farm.data.remote.GardenApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Base64
import javax.inject.Inject

class DiseaseRepository @Inject constructor(private val gardenApi: GardenApi) {
    val cameraProvider = MutableStateFlow<ProcessCameraProvider?>(null)
    val photoFile = MutableStateFlow<File?>(null)
    val cacheDir = MutableStateFlow<File?>(null)
    val imageCapture = MutableStateFlow<ImageCapture?>(null)

    val bitmap = MutableStateFlow<Bitmap?>(null)

    fun setCameraProvider(cameraProvider: ProcessCameraProvider) {
        this.cameraProvider.value = cameraProvider
    }

    fun setCacheDir(cacheDir: File) {
        this.cacheDir.value = cacheDir
    }

    fun setPhotoFile(photoFile: File) {
        this.photoFile.value = photoFile
    }

    fun setImageCapture(imageCapture: ImageCapture) {
        this.imageCapture.value = imageCapture
    }

    fun setPreviewImage(bitmap: Bitmap) {
        this.bitmap.value = bitmap
    }

    fun requestPlantDiseaseDetection() {

        val imageString = bitmap.value?.let { getBase64String(it) }
        if (imageString != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = gardenApi.postDiseaseDetection(imageString)

                if(response.isSuccessful) {
                    Log.e("TEST", response.body()?.data.toString())
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