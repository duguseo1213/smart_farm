package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class GardenDTO(
    val gardenId: BigInteger,
    val gardenName: String,
    val gardenAddress: String,
    val createdDate: Date
)
