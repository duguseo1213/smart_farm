package com.wcd.farm.data.model

data class HarmDTO(
    val harmPictureId: Long,
    val garden: GardenDTO?,
    val harmPicture: String,
    val createdDate: String,
    val harmTarget: String?
)