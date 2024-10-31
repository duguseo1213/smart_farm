package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ForecastWeatherView(onDismissRequest: () -> Unit) {
    val time = LocalDateTime.now(ZoneId.systemDefault())
    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        Button(
            onClick = onDismissRequest,
            colors = buttonTransparentTheme(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("닫기")
        }
    }, title = { Text("주간 예보") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in 1..10) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                ) {
                    Date(time, i)

                    ForecastWeather(0, Icons.Outlined.WbSunny) // 오전
                    Arrow()
                    ForecastWeather(60, Icons.Outlined.WbCloudy) // 오후

                    TemperatureRange(11.0, 23.0)
                }
            }


        }
    })
}

@Composable
fun Date(time: LocalDateTime, plusDay: Int) {
    val nextDate = time.plusDays(plusDay.toLong())

    Text(
        nextDate.format(DateTimeFormatter.ofPattern("MM.dd (E)")),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(0.dp, 4.dp, 8.dp, 0.dp),
        fontSize = 12.sp
    )
}

@Composable
fun ForecastWeather(humidity: Int, icon: ImageVector) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text("$humidity%", fontSize = 12.sp)
        Icon(
            imageVector = icon,
            contentDescription = "sunny",
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
fun TemperatureRange(minTmp: Double, maxTmp: Double) {
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

    Text(tmpText, textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp, 0.dp))
}