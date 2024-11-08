package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class AppViewState(
    val appViewState: String = MAIN
): MavericksState {
    companion object {
        const val LOGIN = "login"
        const val MAIN = "main"
    }
}
