package com.wcd.farm.presentation.state

import com.airbnb.mvrx.MavericksState

data class DiseaseViewState(
    val showDiseaseView: Boolean = false,
    val viewState: Int = 0,
    val showDiseaseDetectResult: Boolean = false,
    val onDiseaseDetect: Boolean = false,
    val isPlantDisease: Boolean = false
): MavericksState