package com.wcd.farm.presentation.view.info

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.R
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun GrowthGraphView(modifier: Modifier) {
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
            /*animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),*/
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
        Text(
            text = "성장 그래프",
            fontFamily = FontFamily(Font(R.font.bookend_semibold)),
            color = Color(0xFF204833),
            fontSize = 22.sp,
            modifier = Modifier
                .background(Color(0xFFFFFCEB))
                .padding(18.dp, 8.dp)
        )
    }
}