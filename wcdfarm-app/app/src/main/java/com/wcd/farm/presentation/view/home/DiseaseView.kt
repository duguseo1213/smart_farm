package com.wcd.farm.presentation.view.home

import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.intent.DiseaseViewIntent
import com.wcd.farm.presentation.state.DiseaseViewState
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel
import java.io.File

@Composable
fun DiseaseScreen(onDismissRequest: () -> Unit) {
    val viewModel: DiseaseViewModel = mavericksViewModel()
    val context = LocalContext.current
    val state by viewModel.collectAsState(DiseaseViewState::viewState)
    val showDiseaseDetectResult by viewModel.collectAsState(DiseaseViewState::showDiseaseDetectResult)
    val diseaseDetect by viewModel.diseaseDetect.collectAsState()
    val onDiseaseDetectState by viewModel.collectAsState(DiseaseViewState::onDiseaseDetect)
    val isPlantDisease by viewModel.collectAsState(DiseaseViewState::isPlantDisease)


    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        when (state) {
            0 -> Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = { viewModel.takePhoto(context) }) {
                    Icon(
                        imageVector = Icons.Outlined.Camera,
                        contentDescription = "Camera",
                        tint = Color.Black
                    )
                }
            }

            1 -> Row(modifier = Modifier.fillMaxWidth()) {
                if (!showDiseaseDetectResult) {
                    TextButton(onClick = { viewModel.showPreviewCaptureView() }) {
                        Text(
                            "재촬영"
                        )
                    }
                    TextButton(onClick = {
                        viewModel.sendIntent(DiseaseViewIntent.ShowDiseaseDetectionResult)
                        viewModel.requestDiseaseDetection()
                    }) { Text("질병 확인") }
                } else {
                    TextButton(onClick = { viewModel.closeDiseaseView() }) {
                        Text("닫기")
                    }
                }

            }
        }


    }, text = {

        Surface(
            modifier = Modifier
        ) {
            when (state) {
                0 -> CameraPreview()
                1 -> {
                    CaptureImage(showDiseaseDetectResult, onDiseaseDetectState, isPlantDisease)
                }
            }
        }
    }, properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val viewModel: DiseaseViewModel = mavericksViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(
            MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image"
        )
    }

    val uri =
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    val cacheDir = uri?.path?.let { File(it) }

    val previewView by remember {
        mutableStateOf(PreviewView(context))
    }

    LaunchedEffect(Unit) {
        viewModel.requestPermission()
        viewModel.startCamera(previewView, lifecycleOwner, cacheDir!!)
    }

    Box(modifier = Modifier) {
        Column {
            AndroidView(factory = { previewView }, update = { })
        }
    }
}

@Composable
fun CaptureImage(showDiseaseDetectResult: Boolean, onDiseaseDetectState: Boolean, isPlantDisease: Boolean) {
    val viewModel: DiseaseViewModel = mavericksViewModel()
    val bitmap by viewModel.bitmap.collectAsState()
    val diseaseDetect by viewModel.diseaseDetect.collectAsState()

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "DiseasePhoto",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                colorFilter = if (showDiseaseDetectResult) ColorFilter.tint(
                    Color.DarkGray, BlendMode.Multiply
                ) else null
            )

            if (showDiseaseDetectResult) {
                if (onDiseaseDetectState) {
                    Text("검출중입니다")
                } else {
                    if (isPlantDisease) {
                        Log.e("TEST", "isPlantDisease: true")
                        Column {
                            Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = "",
                                modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally)
                            )
                            Text("${diseaseDetect?.diseaseName} 검출", color = Color.White)
                            Text("${diseaseDetect?.diseaseSolvent}", color = Color.White)
                        }
                    } else {
                        Log.e("TEST", "isPlantDisease: false")
                        Text("검출되지 않았습니다.", color = Color.White)
                    }
                }
            }
        }
    }
}