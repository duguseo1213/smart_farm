package com.wcd.farm.data.repository

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject

class DiseaseRepository @Inject constructor() {
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
}