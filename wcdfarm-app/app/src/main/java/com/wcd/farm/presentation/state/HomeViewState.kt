package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class HomeViewState(
    val isUserOnFarm: Boolean = false,
    val showWeekWeather: Boolean = false
): MavericksState