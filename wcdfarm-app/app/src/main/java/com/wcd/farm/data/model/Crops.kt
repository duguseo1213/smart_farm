package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class Crops(
    val cropId: BigInteger,
    val gardenId: BigInteger,
    val cropNickname: String,
    val cropName: String,
    val growthStage: String,
    val createdDate: Date
)
