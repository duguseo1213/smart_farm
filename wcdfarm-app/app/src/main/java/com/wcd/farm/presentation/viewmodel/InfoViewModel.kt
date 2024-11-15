package com.wcd.farm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.wcd.farm.data.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val gardenRepository: GardenRepository) :
    ViewModel() {
    val gardenState = gardenRepository.crtGardenState

    val crtGarden = gardenRepository.crtGarden

    fun getGardenState() {
        gardenRepository.getGardenState()
    }
}