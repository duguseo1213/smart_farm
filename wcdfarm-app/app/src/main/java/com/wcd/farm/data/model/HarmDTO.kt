package com.wcd.farm.data.model

import java.time.LocalDateTime

data class HarmDTO(
    val harmPictureId: Long,
    val garden: GardenDTO,
    val harmPicture: String,
    val createdDate: LocalDateTime,
    val harmTarget: String
)
