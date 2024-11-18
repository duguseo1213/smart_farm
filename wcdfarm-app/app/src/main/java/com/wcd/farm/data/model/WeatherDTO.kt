package com.wcd.farm.data.model

data class WeatherDTO(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: String,
    val ny: String,
    val fcstValue: String
)
