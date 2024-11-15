package com.wcd.farm.data.model

import java.time.LocalDateTime

data class PictureDTO(
    val pictureId: Long,
    val pictureUrl: String,
    val pictureDescription: String,
    val pictureDate: String
)
