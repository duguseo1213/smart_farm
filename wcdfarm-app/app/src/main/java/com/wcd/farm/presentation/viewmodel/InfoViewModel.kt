package com.wcd.farm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.wcd.farm.data.model.CropDTO
import com.wcd.farm.data.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val gardenRepository: GardenRepository) :
    ViewModel() {
    val gardenState = gardenRepository.crtGardenState

    val gardenList = gardenRepository.gardenList
    val crtGarden = gardenRepository.crtGarden
    val selectedCrop = gardenRepository.selectedCrop
    val gardenCropList = gardenRepository.gardenCropList
    val recommendCropList = gardenRepository.recommendCropList

    fun getGardenState(gardenId: Long) {
        gardenRepository.getGardenState(gardenId)
    }

    fun getGardenCrops(gardenId: Long) {
        gardenRepository.getGardenCrops(gardenId)
    }

    fun getRecommendCrops(cropName: String) {
        gardenRepository.getRecommendCrops(cropName)
    }

    fun selectCrop(crop: CropDTO) {
        gardenRepository.selectCrop(crop)
    }
}