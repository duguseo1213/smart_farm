package com.wcd.farm.data.model

data class WeatherDTO(
    /*val resultCode: Int,
    val resultMsg: String,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
    val dataType: String,*/
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: String,
    val ny: String,
    val fcstValue: String
)
