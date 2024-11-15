package com.wcd.farm.data.model

import java.math.BigInteger
import java.util.Date

data class GardenState(
    val temperature: Double,
    val humidity: Double,
    val illuminance: Double,
    val soilMoisture: Double,
    val createdDate: Date
)