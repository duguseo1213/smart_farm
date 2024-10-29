package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class Garden(
    val gardenId: BigInteger,
    val gardenName: String,
    val gardenImage: String,
    val createdDate: Date
)
