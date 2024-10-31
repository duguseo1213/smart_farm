package com.wcd.farm.presentation.intent

sealed class HomeViewIntent {
    object ArriveFarm: HomeViewIntent()
    object LeaveFarm: HomeViewIntent()
}