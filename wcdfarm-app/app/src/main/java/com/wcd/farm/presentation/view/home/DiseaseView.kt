package com.wcd.farm.presentation.view.home

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel

@Composable
fun DiseaseScreen(onDismissRequest: () -> Unit) {
    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        TextButton(onClick = {}) {
            Text(text = "촬영")
        }
    }, dismissButton = {
        TextButton(onClick = onDismissRequest) {
            Text(text = "취소")
        }
    }, text = {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CameraPreview()
        }
    },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val viewModel: DiseaseViewModel = mavericksViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView by remember {
        mutableStateOf(PreviewView(context))
    }

    LaunchedEffect(Unit) {
        viewModel.requestPermission()
        viewModel.startCamera(previewView, lifecycleOwner)
    }

    Box(modifier = Modifier) {
        Column {
            AndroidView(
                factory = { previewView },
                update = { }
            )
        }
    }
}