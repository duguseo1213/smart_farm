package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class TimeLapseImageDTO(
    val gardenId: BigInteger,
    val image: String,
    val createdDate: String
)
