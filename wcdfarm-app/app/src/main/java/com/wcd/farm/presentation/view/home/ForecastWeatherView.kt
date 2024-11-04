package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Water
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcd.farm.data.model.ForecastWeather
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.WeatherViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ForecastWeatherView(onDismissRequest: () -> Unit) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val forecastWeather by weatherViewModel.forecastWeather.collectAsState()
    val time = LocalDateTime.now(ZoneId.systemDefault())

    val codeWeatherMap = mapOf(
        -1 to Icons.Outlined.QuestionMark,
        0 to Icons.Outlined.WbSunny,
        1 to Icons.Outlined.WbCloudy,
        2 to Icons.Outlined.Water, // rainy
        3 to Icons.Outlined.Circle // snow
    )

    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        Button(
            onClick = onDismissRequest,
            colors = buttonTransparentTheme(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("닫기")
        }
    }, title = { Text("주간 예보") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 1..7) {
                val weather = forecastWeather[i - 1]
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Date(Modifier.weight(0.35f), time, i)

                    ForecastWeather(Modifier.weight(0.2f), weather.amRainProbability, codeWeatherMap[weather.amWeather]!!) // 오전
                    Arrow()
                    ForecastWeather(Modifier.weight(0.2f), weather.pmRainProbability, codeWeatherMap[weather.amWeather]!!) // 오후

                    TemperatureRange(Modifier.weight(0.3f), weather.minTmp, weather.maxTmp)
                }
            }


        }
    }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun Date(modifier: Modifier, time: LocalDateTime, plusDay: Int) {
    val nextDate = time.plusDays(plusDay.toLong())

    Text(
        nextDate.format(DateTimeFormatter.ofPattern("MM.dd (E)")),
        modifier = modifier.padding(0.dp, 4.dp, 8.dp, 0.dp),
        fontSize = 12.sp
    )
}

@Composable
fun ForecastWeather(modifier: Modifier, humidity: Int, icon: ImageVector) {
    Row(verticalAlignment = Alignment.Bottom, modifier = modifier) {
        Text("$humidity%", fontSize = 12.sp)
        Icon(
            imageVector = icon,
            contentDescription = "weather",
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun Arrow() {
    Icon(
        imageVector = Icons.AutoMirrored.Outlined.ArrowRight,
        contentDescription = "Arrow",
        modifier = Modifier
    )
}

@Composable
fun TemperatureRange(modifier: Modifier, minTmp: Int, maxTmp: Int) {
    val tmpText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 10.sp,
                color = Color.Blue,
                baselineShift = BaselineShift(0.2f)
            )
        ) {
            append("$minTmp°")
        }
        withStyle(style = SpanStyle()) {
            append("/")
        }
        withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.Red)) {
            append("$maxTmp°")
        }
    }

    Text(tmpText, modifier = modifier.padding(8.dp, 0.dp))
}