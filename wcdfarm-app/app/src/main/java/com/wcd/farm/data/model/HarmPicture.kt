package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class HarmPicture(
    val harmPictureId: BigInteger,
    val gardenId: BigInteger,
    val harmPicture: String,
    val createdDate: Date,
    val harmTarget: String
)
