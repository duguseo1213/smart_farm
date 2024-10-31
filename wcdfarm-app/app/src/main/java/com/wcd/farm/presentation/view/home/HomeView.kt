package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = mavericksViewModel()
    val showWeekWeather by homeViewModel.collectAsState(HomeViewState::showWeekWeather)

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TodayWeatherView()
        Spacer(modifier = Modifier.fillMaxHeight(0.4f))
        MenuContainer()
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        StateView()

        if (showWeekWeather) {
            ForecastWeatherView {
                homeViewModel.sendIntent(HomeViewIntent.HideWeekWeather)
            }
        }
    }
}