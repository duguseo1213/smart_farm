package com.wcd.farm.presentation.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.R
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme

@Composable
fun MainScreen() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TodayWeatherView()
        Spacer(modifier = Modifier.fillMaxHeight(0.4f))
        MenuContainer()
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        StateView()
    }
}

@Composable
fun TodayWeatherView() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
    ) {
        val width = maxWidth
        val height = maxHeight

        Text(
            "Farm-us",
            modifier = Modifier.offset(x = width / 12, y = height / 6),
            color = Color.White,
            fontSize = 30.sp,
            letterSpacing = (-2).sp,
            fontWeight = FontWeight.Light
        )

        Icon(
            Icons.Outlined.WbSunny,
            "RAINY",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = width / 10)
                .size(72.dp)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)/*modifier = Modifier.offset(x = width * 3 / 10, y = height / 2)*/,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TemperatureView()
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(-width / 10, 0.dp)
        ) {
            WeatherStatusView()
        }

    }
}

@Composable
fun TemperatureView() {
    Text("24°C", fontSize = 36.sp, color = Color.White)
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(0.3f)) {

        val styledText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 10.sp)) {
                append("최대 ")
            }
            withStyle(style = SpanStyle(fontSize = 16.sp)) {
                append("33°C")
            }
        }

        Text(styledText, color = Color.White, fontWeight = FontWeight.Light)
        Text("|", fontSize = 16.sp, color = Color.White)
        Text(styledText, color = Color.White, fontWeight = FontWeight.Light)
    }
}

@Composable
fun WeatherStatusView() {
    Text("강수: 3mm", color = Color.White)
    Text("바람: 2m/s", color = Color.White)
    Text("습도: 80%", color = Color.White)
}

@Composable
fun MenuContainer() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        MenuButton(Icons.Outlined.WaterDrop, "물주기")
        MenuButton(Icons.Outlined.PhotoCamera, "사진 촬영")
        MenuButton(Icons.Outlined.Grass, "질병 확인")
    }
}

@Composable
fun MenuButton(icon: ImageVector, description: String) {
    
    Button(
        onClick = { /*TODO*/ },
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.menu_background),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Water",
                    modifier = Modifier.size(36.dp)
                )
                Text(description, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StateView() {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.8f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(24.dp, 4.dp, 24.dp, 24.dp)
    ) {
        StateBar(icon = Icons.Outlined.WaterDrop, 90, Color.Cyan)
        Spacer(modifier = Modifier.width(24.dp))
        StateBar(icon = Icons.Outlined.WbSunny, ratio = 40, Color.Yellow)
        Spacer(modifier = Modifier.width(24.dp))
        StateBar(icon = Icons.Outlined.LocalHospital, ratio = 5, Color.Magenta)
    }
}

@Composable
fun StateBar(icon: ImageVector, ratio: Int, color: Color) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Water",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        var height by remember {
            mutableFloatStateOf(0f)
        }
        var textHeight by remember {
            mutableIntStateOf(0)
        }
        Box(modifier = Modifier, contentAlignment = if(height > textHeight) Alignment.TopCenter else Alignment.BottomCenter) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxHeight(ratio.toFloat() / 100)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .width(48.dp)
                    .background(color)
            ) {
                height = maxHeight.value
            }
            Text("$ratio%", modifier = Modifier
                .padding(4.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    textHeight = layoutCoordinates.size.height
                },
                color = if(height > textHeight) Color.White else Color.Red)
        }

    }
}