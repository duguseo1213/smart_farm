package com.wcd.farm.data.model

data class ForecastWeather(
    val amRainProbability: Int = 0,
    val pmRainProbability: Int = 0,
    val amWeather: Int = -1,
    val pmWeather: Int = -1,
    val minTmp: Int = 0,
    val maxTmp: Int = 0
)
