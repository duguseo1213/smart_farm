package com.wcd.farm.data.repository

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.wcd.farm.data.LatLngConverter
import com.wcd.farm.data.LatXLngY
import com.wcd.farm.data.model.ForecastWeather
import com.wcd.farm.data.model.WeatherDTO
import com.wcd.farm.data.model.WeatherInfo
import com.wcd.farm.data.remote.MeteoApi
import com.wcd.farm.data.remote.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi, private val meteoApi: MeteoApi) {

    private val _latXLngYList = MutableStateFlow(mutableListOf<LatXLngY>())
    val latXLngYList = _latXLngYList.asStateFlow()

    private val _weatherList = MutableStateFlow(mutableListOf<WeatherDTO>())
    val weatherList = _weatherList.asStateFlow()

    private val _weather = MutableStateFlow(WeatherInfo())
    val weather = _weather.asStateFlow()

    private val _forecastWeather = MutableStateFlow(List(7) { ForecastWeather() })
    val forecastWeather = _forecastWeather.asStateFlow()

    fun getLiveWeather(lat: Double, lng: Double, time: LocalDateTime) {
        val coord = LatLngConverter.convertGRID_GPS(LatLngConverter.TO_GRID, lat, lng)

        if (time.minute < 10) {
            time.minusHours(1)
        }
        val baseTime = String.format(
            Locale.KOREA,
            "%02d",
            if (time.minute < 10) time.minusHours(1).hour
            else time.hour
        ) + "00"
        Log.e("TEST", baseTime)
        val baseDate = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

        val customOptions: MutableMap<String, String> = mutableMapOf()
        customOptions["base_date"] = baseDate
        customOptions["base_time"] = baseTime
        customOptions["nx"] = coord.x.toInt().toString()
        customOptions["ny"] = coord.y.toInt().toString()

        val options = getOptions(customOptions)
        CoroutineScope(Dispatchers.IO).launch {

            val response = weatherApi.getLiveWeather(options)

            if (response.isSuccessful) {
                val itemArray = getItem(response.body()!!)
                val weatherList = itemArray.asList()

                val weatherInfo = WeatherInfo()

                for (weatherString in weatherList) {
                    val liveWeather = weatherString.asJsonObject
                    val category = liveWeather["category"].asString
                    val value = liveWeather["obsrValue"]
                   // if(category == "SKY" )Log.e("TEST", "SKY: " + value.asString)
                    when (category) {
                        "T1H" -> weatherInfo.tmp = value.asDouble
                        "RN1" -> weatherInfo.rain = value.asString
                        "REH" -> weatherInfo.humidity = value.asInt
                        "WSD" -> weatherInfo.wind = value.asDouble
                        "PTY" -> weatherInfo.isRain = value.asInt
                    }
                }
                _weather.value = weatherInfo
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }

    fun getNearForecastWeather(lat: Double, lng: Double, time: LocalDateTime) {
        val baseDate = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val baseTime = "0200"
        val coord = LatLngConverter.convertGRID_GPS(LatLngConverter.TO_GRID, lat, lng)

        val customOptions: MutableMap<String, String> = mutableMapOf()
        customOptions["base_date"] = baseDate
        customOptions["base_time"] = baseTime
        customOptions["nx"] = coord.x.toInt().toString()
        customOptions["ny"] = coord.y.toInt().toString()

        val options = getOptions(customOptions)
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.getNearWeather(options)

            if (response.isSuccessful) {
                val itemArray = getItem(response.body()!!)
                val weatherList = itemArray.asList()

                val list = _forecastWeather.value.toMutableList()
                for (weather in weatherList) {
                    val weatherObject = weather.asJsonObject
                    val category = weatherObject["category"].asString
                    val fcstDate = weatherObject["fcstDate"].asString
                    val fcstTime = weatherObject["fcstTime"].asString
                    val fcstValue = weatherObject["fcstValue"].asString

                    val index = fcstDate.toInt() - baseDate.toInt() - 1
                    when (category) {
                        "TMN" -> {
                            val minTmp = fcstValue.toDouble()
                            if (baseDate == fcstDate) {
                                _weather.value = _weather.value.copy(minTmp = minTmp)
                            } else {
                                list[index] = list[index].copy(minTmp = minTmp.toInt())
                            }
                        }

                        "TMX" -> {
                            val maxTmp = fcstValue.toDouble()
                            if (baseDate == fcstDate) {
                                _weather.value = _weather.value.copy(maxTmp = maxTmp)
                            } else {
                                list[index] = list[index].copy(maxTmp = maxTmp.toInt())
                            }
                        }

                        "POP" -> {
                            if (baseDate != fcstDate) {
                                val rainProbability = fcstValue.toInt()
                                if (fcstTime == "0900") {
                                    list[index] =
                                        list[index].copy(amRainProbability = rainProbability)
                                } else if (fcstTime == "1800") {
                                    list[index] =
                                        list[index].copy(pmRainProbability = rainProbability)
                                }
                            }

                        }

                        "SKY" -> {
                            val skyType = fcstValue.toInt()
                            if (baseDate != fcstDate) {
                                val weatherType = if (skyType == 1) 1 else 2
                                if (fcstTime == "0900") {
                                    list[index] = list[index].copy(amWeather = weatherType)
                                } else if (fcstTime == "1800") {
                                    list[index] = list[index].copy(pmWeather = weatherType)
                                }
                            }
                        }

                        "PTY" -> {
                            val rainType = fcstValue.toInt()
                            if (baseDate != fcstDate && rainType != 0) {
                                val weatherType: Int = if (rainType != 3) 2 else 3
                                if (fcstTime == "0900") {
                                    list[index] = list[index].copy(amWeather = weatherType)
                                } else if (fcstTime == "1800") {
                                    list[index] = list[index].copy(pmWeather = weatherType)
                                }
                            }
                        }
                    }
                }

                _forecastWeather.value = list
            }
        }
    }

    fun getForecastWeather(latitude: Double, longitude: Double, time: LocalDateTime) {



        CoroutineScope(Dispatchers.IO).launch {

            val areaCodeResponse = meteoApi.getAreaCode(latitude, longitude)

            if(areaCodeResponse.isSuccessful) {
                val areaCode = areaCodeResponse.body()!!.data.areacode
                val baseTime = time.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"
                val customOptions: MutableMap<String, String> = mutableMapOf()
                customOptions["regId"] = areaCode
                customOptions["tmFc"] = baseTime
                val options = getOptions(customOptions)

                val response = weatherApi.getForecastWeather(options)
                if (response.isSuccessful) {
                    val list = _forecastWeather.value.toMutableList()
                    val item = getItem(response.body()!!)[0].asJsonObject
                    for (i in 3..7) {
                        val amRainPredictField = "rnSt${i}Am"
                        val pmRainPredictField = "rnSt${i}Pm"
                        val amWeatherField = "wf${i}Am"
                        val pmWeatherField = "wf${i}Pm"

                        val amRainPredict = item[amRainPredictField].asInt
                        val pmRainPredict = item[pmRainPredictField].asInt

                        val amWeather = item[amWeatherField].asString
                        val pmWeather = item[pmWeatherField].asString
                        val amWeatherCode = weatherStringToCode(amWeather)
                        val pmWeatherCode = weatherStringToCode(pmWeather)

                        list[i - 1] = list[i - 1].copy(
                            amRainProbability = amRainPredict,
                            pmRainProbability = pmRainPredict,
                            amWeather = amWeatherCode,
                            pmWeather = pmWeatherCode
                        )
                    }

                    _forecastWeather.value = list
                }

                val customOptions2: MutableMap<String, String> = mutableMapOf()
                customOptions2["regId"] = "11F20501"
                customOptions2["tmFc"] = baseTime

                val options2 = getOptions(customOptions2)

                val response2 = weatherApi.getForecastTmp(options2)

                if (response2.isSuccessful) {
                    val list = _forecastWeather.value.toMutableList()

                    val item = getItem(response2.body()!!)[0].asJsonObject
                    for (i in 3..7) {
                        val tmpMinField = "taMin$i"
                        val tmpMaxField = "taMax$i"

                        val minTmp = item[tmpMinField].asInt
                        val maxTmp = item[tmpMaxField].asInt

                        list[i - 1] = list[i - 1].copy(minTmp = minTmp, maxTmp = maxTmp)
                    }
                    _forecastWeather.value = list
                }
            }


        }
    }

    private fun getOptions(customOptions: Map<String, String>): Map<String, String> {
        val options: MutableMap<String, String> = mutableMapOf()

        options["serviceKey"] =
            "jbpUUy%2FMmr0n5alZ225PBgJL%2FiRcdbJxJ3%2BxyfBpbCCj3Kt0venEiltIO2xP2duLf4BBqytQ7cPYYGaGaNBUPg%3D%3D"
        options["numOfRows"] = "1000"
        options["pageNo"] = "1"
        options["dataType"] = "JSON"

        options.putAll(customOptions)

        return options
    }

    private fun getItem(body: String): JsonArray {
        val jsonObject = JsonParser.parseString(body).asJsonObject
        val responseObject = jsonObject["response"].asJsonObject
        val responseBody = responseObject["body"].asJsonObject
        val itemsArray = responseBody["items"].asJsonObject["item"].asJsonArray

        return itemsArray
    }

    private fun weatherStringToCode(weather: String): Int {
        return if (weather == "맑음") 0
        else if (weather == "구름많음" || weather == "흐림") 1
        else if (weather.endsWith("비")
            || weather.endsWith("소나기")
        ) 2
        else if (weather.endsWith("눈")) 3
        else -1
    }
}