package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
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
    val newPicture by homeViewModel.newPicture.collectAsState()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(crtGarden) {
        val longitude = 126.8071876
        val latitude = 35.2040949
        crtGarden?.let { infoViewModel.getGardenState(gardenList[it].gardenId) }
        weatherViewModel.getLiveWeather(latitude, longitude)
    }

    val pagerState = rememberPagerState { gardenList.size }

    LaunchedEffect(pagerState.currentPage) {
        homeViewModel.setCrtGarden(pagerState.currentPage)
        //homeViewModel.setCrtGarden(gardenList.size - pagerState.currentPage - 1)
    }

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize().padding(4.dp)
                .addFocusCleaner(focusManager), horizontalAlignment = Alignment.CenterHorizontally) {
            TodayWeatherView(Modifier.weight(0.16f))
            Spacer(Modifier.weight(0.01f))
            MyFarmView(modifier = Modifier.weight(0.35f), focusManager)
            Spacer(Modifier.weight(0.03f))
            MenuContainer(modifier = Modifier.weight(0.1f))
            Spacer(modifier = Modifier.weight(0.03f))
            StateView(modifier = Modifier.weight(0.22f))

            if (showWeekWeather) {
                ForecastWeatherView {
                    homeViewModel.sendIntent(HomeViewIntent.HideWeekWeather)
                }
            }

            if (newPicture != null) {
                NewPictureView()
            }
        }
    }


}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}