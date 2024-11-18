package com.wcd.farm.presentation.view.info

import androidx.annotation.FontRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.presentation.view.home.StateBar
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.viewmodel.InfoViewModel

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
    val infoViewModel: InfoViewModel = hiltViewModel()
    val gardenState by infoViewModel.gardenState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxHeight()
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "농장 기온",
            color = Color(0xFF1A422D),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.bookend_semibold))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${(gardenState?.temperature?.toInt() ?: 0)}°C",
            color = Color(0xFF1A422D),
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(R.font.bookend_semibold))
        )
    }
}


@Composable
fun CrtFarmStateView(modifier: Modifier) {
    val infoViewModel: InfoViewModel = hiltViewModel()
    val gardenState by infoViewModel.gardenState.collectAsState()
    Row(verticalAlignment = Alignment.Bottom, modifier = modifier.fillMaxHeight()) {
        Spacer(modifier = Modifier.width(10.dp))
        StateBar(icon = Icons.Outlined.WaterDrop, gardenState?.humidity?.toInt() ?: 100, color = Color(0xFF86C6C6), iconColor = Color(0xFF86C6C6))
        Spacer(modifier = Modifier.width(36.dp))
        StateBar(icon = Icons.Outlined.WbSunny, gardenState?.illuminance?.toInt() ?: 100, color = Color(0xFFFFD000), iconColor = Color(0xFFFFD000))
    }
}


