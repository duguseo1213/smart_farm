package com.wcd.farm.presentation.view.info

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.presentation.view.home.StateBar
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun InfoScreen() {
    Column(Modifier.fillMaxHeight()) {
        CrtState(Modifier.weight(0.2f).clip(RoundedCornerShape(16.dp)).shadow(4.dp, RoundedCornerShape(16.dp))) // 0.25
        Spacer(modifier = Modifier.weight(0.05f))
        SelectCrops(Modifier.weight(0.075f).clip(RoundedCornerShape(16.dp))) // 0.1f
        Spacer(modifier = Modifier.weight(0.025f))
        GrowthGraph(Modifier.weight(0.4f).clip(RoundedCornerShape(16.dp))) // 0.4f
        Spacer(modifier = Modifier.weight(0.05f))
        Recommend(Modifier.weight(0.2f).clip(RoundedCornerShape(16.dp))) // 0.25f
        Spacer(modifier = Modifier.weight(0.025f))
    }
}

@Composable
fun CrtState(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFCEB))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
        ) {
            Icon(imageVector = Icons.Outlined.Cloud, contentDescription = "Cloud")
            Text("23°C")
        }
        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.weight(0.5f)) {
            StateBar(icon = Icons.Outlined.WaterDrop, ratio = 100, color = Color.Cyan)
            Spacer(modifier = Modifier.width(36.dp))
            StateBar(icon = Icons.Outlined.WbSunny, ratio = 80, color = Color.Yellow)
        }

    }
}

@Composable
fun SelectCrops(modifier: Modifier) {
    Button(
        onClick = { /*TODO*/ },
        colors = buttonTransparentTheme(),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0XFFFFFCEB)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "상추",
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp, 0.dp)
            )
            Image(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "DropDown",
                modifier = Modifier.padding(8.dp, 0.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }


    DropdownMenu(
        expanded = false, onDismissRequest = { /*TODO*/ }, modifier = Modifier
            .fillMaxHeight(0.05f)
            .fillMaxWidth()
    ) {
        Text("상추")
    }
}

@Composable
fun GrowthGraph(modifier: Modifier) {
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
                    color = SolidColor(Color(0xFF23af92)),
                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
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
                color = SolidColor(Color.Red),
                radius = 5.dp,
            ),
            maxValue = 100.0,
            minValue = 0.0

        )
        Text(
            "성장 그래프",
            Modifier
                .background(Color(0XFFFFFCEB))
                .padding(18.dp, 8.dp), fontSize = 22.sp
        )
    }
}

@Composable
fun Recommend(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFFFFFCEB)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1.0f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Grass,
                contentDescription = "crops",
                modifier = Modifier.size(36.dp)
            )
            Text("추천 조합")
        }
        VerticalDivider(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(0.6f), // Divider가 Column의 전체 높이를 차지하도록 설정
            color = Color.Black.copy(alpha = 0.2f) // Divider 색상
        )

        Column(modifier = Modifier.weight(2.0f)) {

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFECEDC1, widthDp = 360, heightDp = 640)
@Composable
fun PreviewInfoScreen() {
    InfoScreen()
}