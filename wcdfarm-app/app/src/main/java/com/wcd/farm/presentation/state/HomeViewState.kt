package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class HomeViewState(
    val isUserOnFarm: Boolean = false,
    val showWeekWeather: Boolean = false,
    val displayType: Int = IMAGE
): MavericksState {
    companion object {
        const val IMAGE = 1
        const val LIVE = 2
    }
}