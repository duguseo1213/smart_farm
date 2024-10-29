package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class HarmVideo(
    val harmVideoId: BigInteger,
    val harmPictureId: BigInteger,
    val gardenId: BigInteger,
    val harmVideo: String,
    val createdDate: Date
)
