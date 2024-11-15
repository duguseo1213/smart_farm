package com.wcd.farm.presentation.intent

sealed class HomeViewIntent {
    object ArriveFarm: HomeViewIntent()
    object LeaveFarm: HomeViewIntent()
    object ShowWeekWeather: HomeViewIntent()
    object HideWeekWeather: HomeViewIntent()
    object ShowFarmImage: HomeViewIntent()
    object ShowFarmLive: HomeViewIntent()
}