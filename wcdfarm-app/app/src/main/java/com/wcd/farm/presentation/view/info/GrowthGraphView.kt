package com.wcd.farm.presentation.view.info

import android.content.ContentValues
import android.provider.MediaStore
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.view.home.noRippleClickable
import com.wcd.farm.presentation.view.memorial.customFontFamily3
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import java.io.File

@Composable
fun GrowthGraphView(modifier: Modifier) {
    var showGrowthPredictionCamera by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFCEB))
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            data = listOf(
                Line(
                    label = "Windows",
                    values = listOf(12.0, 26.0, 42.0, 66.0, 90.0),
                    color = SolidColor(Color(0xFF0F4327)),
                    firstGradientFillColor = Color(0xFF1A874D).copy(alpha = 1.0f),
                    secondGradientFillColor = Color(0xFFFFFCEB),
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                    curvedEdges = false

                )
            ),
            indicatorProperties = HorizontalIndicatorProperties(false),
            dividerProperties = DividerProperties(false),
            gridProperties = GridProperties(false),
            labelProperties = LabelProperties(
                true,
                textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
                labels = listOf("1주", "2주", "3주", "4주", "5주"),
            ),
            dotsProperties = DotProperties(
                enabled = true,
                color = SolidColor(Color(0xFF1A6D40)),
                radius = 3.dp,
            ),
            maxValue = 100.0,
            minValue = 0.0

        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp, 18.dp, 0.dp)
                .background(Color(0XFFFFFCEB))
        ) {
            Text(
                text = "성장 그래프",
                fontFamily = FontFamily(Font(R.font.bookend_semibold)),
                color = Color(0xFF204833),
                fontSize = 22.sp,
                modifier = Modifier
                    .background(Color(0xFFFFFCEB))
                    .padding(18.dp, 12.dp)
            )
            Text(
                "더 정확하게 성장시기 예측하기!",
                modifier = Modifier
                    .border(1.dp, Color(0XFF197142), RoundedCornerShape(12.dp))
                    .background(Color(0XFFECEDC1), RoundedCornerShape(12.dp))
                    .padding(8.dp)
                    .noRippleClickable { /*showGrowthPredictionCamera = true*/ }, letterSpacing = (-1).sp,
                fontFamily = customFontFamily3
            )
        }
    }

    if (showGrowthPredictionCamera) {
        GrowthPredictionCameraView()
    }
}

@Composable
fun GrowthPredictionCameraView() {
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

    Box(modifier = Modifier
        .fillMaxSize()
        .drawWithContent {
            drawContent()
            drawRect(color = Color.Black.copy(alpha = 0.6f))
        }) {
        Column(modifier = Modifier
            .fillMaxSize(0.8f)
            .align(Alignment.Center)) {
            AndroidView(factory = { previewView }, update = { })
        }
    }

}