package com.wcd.farm.data.model

data class WeatherInfo(
    var tmp: Double = -100.0,
    var minTmp: Double = 0.0,
    var maxTmp: Double = 0.0,
    var rain: String = "강수 없음",
    var wind: Double = 0.0,
    var humidity: Int = 0,
    var sky: Int = 0,
    var isRain: Int = 0
)
