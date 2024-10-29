package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class TimeLapse(
    val timeLapseId: BigInteger,
    val gardenId: BigInteger,
    val timeLapseImage: String,
    val createdDate: Date
)
