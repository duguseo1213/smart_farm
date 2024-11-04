package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class DiseaseViewState(
    val showDiseaseView: Boolean = false,
    val viewState: Int = 0
): MavericksState