package com.wcd.farm.data.model

data class WeatherListDTO(
    val dataType: String,
    val item: List<WeatherDTO>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)