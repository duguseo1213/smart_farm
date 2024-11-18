package com.wcd.farm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.wcd.farm.data.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val gardenRepository: GardenRepository): ViewModel() {

    val cropList = gardenRepository.gardenCropList
    val gardenList = gardenRepository.gardenList
    val crtGarden = gardenRepository.crtGarden


    fun addCrop(gardenId: Long, cropName: String) {
        gardenRepository.addCrop(gardenId, cropName)
    }

    fun deleteCrop(gardenId: Long, cropName: String) {
        gardenRepository.deleteCrop(gardenId, cropName)
    }

    fun getCrops(gardenId: Long) {
        gardenRepository.getGardenCrops(gardenId)
    }
}