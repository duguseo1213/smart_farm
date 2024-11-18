package com.wcd.farm.presentation.view.home

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = mavericksViewModel()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val infoViewModel: InfoViewModel = hiltViewModel()
    val showWeekWeather by homeViewModel.collectAsState(HomeViewState::showWeekWeather)
    val gardenList by homeViewModel.gardenList.collectAsState()
    val crtGarden by homeViewModel.crtGarden.collectAsState()
    val crtWeatherGardenIndex by homeViewModel.crtWeatherGardenIndex.collectAsState()
    val newPicture by homeViewModel.newPicture.collectAsState()
    val gardenState by infoViewModel.gardenState.collectAsState()
    val isUserOnFarm by homeViewModel.collectAsState(HomeViewState::isUserOnFarm)

    val focusManager = LocalFocusManager.current

    LaunchedEffect(crtGarden) {
        crtGarden?.let {
            infoViewModel.getGardenState(gardenList[it].gardenId)
            if (crtGarden != crtWeatherGardenIndex) {
                val latitude = gardenList[crtGarden!!].lat
                val longitude = gardenList[crtGarden!!].lon

                Log.e("TEST", "latitude: $latitude, longitude: $longitude")

                /*weatherViewModel.getNearForecastWeather(latitude, longitude)
                weatherViewModel.getForecastWeather(latitude, longitude)
                weatherViewModel.getLiveWeather(latitude, longitude)*/
                crtGarden?.let { homeViewModel.setCrtWeatherGardenIndex(it) }
            }
        }

        while (true) {
            delay(5000)
            crtGarden?.let {
                infoViewModel.getGardenState(gardenList[it].gardenId)
            }
        }
    }

    val pagerState = rememberPagerState { gardenList.size }

    LaunchedEffect(pagerState.currentPage) {
        homeViewModel.setCrtGarden(pagerState.currentPage)
    }

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(4.dp)
                .addFocusCleaner(focusManager), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodayWeatherView(Modifier.weight(0.16f))
            Spacer(Modifier.weight(0.01f))
            MyFarmView(modifier = Modifier.weight(0.35f), focusManager)
            Spacer(Modifier.weight(0.03f))
            MenuContainer(modifier = Modifier.weight(0.1f), isUserOnFarm)
            Spacer(modifier = Modifier.weight(0.03f))
            StateView(modifier = Modifier.weight(0.22f), isUserOnFarm)

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