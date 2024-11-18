package com.wcd.farm.data.model

data class GardenDTO(
    val gardenId: Long = -1,
    val gardenName: String = "",
    val gardenAddress: String = "",
    val gardenCreated: String = "",
    val gardenImage: String,
    val lat: Double,
    val lon: Double,
)
