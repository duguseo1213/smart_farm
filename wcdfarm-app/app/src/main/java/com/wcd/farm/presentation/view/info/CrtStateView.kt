package com.wcd.farm.presentation.view.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.presentation.view.home.StateBar
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CrtStateView(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0XFFFFFCEB))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CrtWeatherView(Modifier.weight(0.5f))
        CrtFarmStateView(Modifier.weight(0.5f))

    }
}

@Composable
fun CrtWeatherView(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxHeight()
    ) {
        Icon(imageVector = Icons.Outlined.Cloud, contentDescription = "Cloud")
        Text("23°C")
    }
}

@Composable
fun CrtFarmStateView(modifier: Modifier) {
    Row(verticalAlignment = Alignment.Bottom, modifier = modifier) {
        StateBar(icon = Icons.Outlined.WaterDrop, ratio = 100, color = Color(0xFF86C6C6))
        Spacer(modifier = Modifier.width(36.dp))
        StateBar(icon = Icons.Outlined.WbSunny, ratio = 80, color = Color(0xFFFFD000))
    }
}


