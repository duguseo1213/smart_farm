package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class TimeLapseDTO(
    val gardenId: BigInteger,
    val timeLapseImage: String,
    val createdDate: Date
)
