package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import com.wcd.farm.presentation.viewmodel.InfoViewModel
import com.wcd.farm.presentation.viewmodel.WeatherViewModel

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = mavericksViewModel()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val infoViewModel: InfoViewModel = hiltViewModel()
    val showWeekWeather by homeViewModel.collectAsState(HomeViewState::showWeekWeather)
    val gardenList by homeViewModel.gardenList.collectAsState()
    val crtGarden by homeViewModel.crtGarden.collectAsState()

    LaunchedEffect(Unit) {
        val longitude = 126.8071876
        val latitude = 35.2040949

        weatherViewModel.getLiveWeather(latitude, longitude)
        crtGarden?.let { infoViewModel.getGardenState(it.gardenId) }
    }

    LaunchedEffect(crtGarden) {
        crtGarden?.let { infoViewModel.getGardenState(it.gardenId) }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TodayWeatherView(Modifier.weight(0.16f))
        Spacer(Modifier.weight(0.01f))
        MyFarmView(modifier = Modifier.weight(0.35f))
        //ExoPlayerUI(modifier = Modifier.weight(0.35f))
        Spacer(Modifier.weight(0.03f))
        MenuContainer(modifier = Modifier.weight(0.1f))
        Spacer(modifier = Modifier.weight(0.03f))
        StateView(modifier = Modifier.weight(0.22f))

        if (showWeekWeather) {
            ForecastWeatherView {
                homeViewModel.sendIntent(HomeViewIntent.HideWeekWeather)
            }
        }
    }
}