package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class MemorialViewState(
    val showMemorialView: Int = 1,
    val showDialog: Boolean = false,
    val showInvasionVideo: Boolean = false
): MavericksState
