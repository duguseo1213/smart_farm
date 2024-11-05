package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import com.wcd.farm.presentation.viewmodel.WeatherViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font

import com.wcd.farm.R

val customFontFamily_weather_main1 = FontFamily(Font(R.font.bookend_semibold))
val customFontFamily_weather_main2 = FontFamily(Font(R.font.ef_yoony))
@Composable
fun TodayWeatherView() {
    val homeViewModel: HomeViewModel = mavericksViewModel()

    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val weather by weatherViewModel.weather.collectAsState()

    if (weather.tmp == 0.0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Button(
            onClick = { homeViewModel.sendIntent(HomeViewIntent.ShowWeekWeather) },
            contentPadding = PaddingValues(0.dp),
            colors = buttonTransparentTheme(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.17f)
            ) {
                HomeServiceNameView()
                TodayWeatherIcon(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )
                TemperatureView(modifier = Modifier.align(Alignment.BottomCenter))
                WeatherStatusView(modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}

@Composable
fun HomeServiceNameView() {
    Text(
        "Farm-us",
        color = Color(0xFF28543D),
        fontSize = 40.sp,
        letterSpacing = (-2).sp,
        fontWeight = FontWeight.Medium,
        fontFamily = customFontFamily_weather_main1
    )
}

@Composable
fun TodayWeatherIcon(modifier: Modifier) {
    Icon(
        Icons.Outlined.WbSunny,
        "RAINY",
        tint = Color(0xFF28543D),
        modifier = modifier
            .size(72.dp)
    )
}

@Composable
fun TemperatureView(modifier: Modifier) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val weather by weatherViewModel.weather.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "${weather.tmp}°C",
            fontSize = 40.sp,
            color = Color(0xFF28543D),
            fontFamily = customFontFamily_weather_main1)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth(0.4f)
        ) {
            BuildTmpText(attr = "최소", tmp = weather.minTmp)
            Text("|", fontSize = 14.sp, color = Color(0xFF28543D), fontWeight = FontWeight.Light)
            BuildTmpText(attr = "최대", tmp = weather.maxTmp)
        }
    }
}

@Composable
fun BuildTmpText(attr: String, tmp: Double) {
    val tmpText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 10.sp)) {
            append("$attr ")
        }
        withStyle(style = SpanStyle(fontSize = 14.sp)) {
            append("$tmp°C")
        }
    }

    Text(tmpText, color = Color(0xFF28543D), fontWeight = FontWeight.Light)
}

@Composable
fun WeatherStatusView(modifier: Modifier) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val weather by weatherViewModel.weather.collectAsState()

    Column(modifier = modifier) {
        Text("강수: ${weather.rain}mm", color = Color(0xFF28543D), fontWeight = FontWeight.SemiBold)
        Text("바람: ${weather.wind}m/s", color = Color(0xFF28543D))
        Text("습도: ${weather.humidity}%", color = Color(0xFF28543D))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTest() {
    TodayWeatherView()
}