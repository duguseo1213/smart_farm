package com.wcd.farm.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.wcd.farm.data.LatLngConverter
import com.wcd.farm.data.LatXLngY
import com.wcd.farm.data.model.WeatherDTO
import com.wcd.farm.data.model.WeatherInfo
import com.wcd.farm.data.remote.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    private val _latXLngYList = MutableStateFlow(mutableListOf<LatXLngY>())
    val latXLngYList = _latXLngYList.asStateFlow()

    private val _weatherList = MutableStateFlow(mutableListOf<WeatherDTO>())
    val weatherList = _weatherList.asStateFlow()

    private val _weather = MutableStateFlow(WeatherInfo())
    val weather = _weather.asStateFlow()

    fun getCrtWeather(index: Int, lat: Double, lng: Double, time: LocalDateTime) {
        val options: MutableMap<String, String> = mutableMapOf()

        val longitude = 126.8071876 // 경도
        val latitude = 35.2040949 // 위도

        val coord = LatLngConverter.convertGRID_GPS(LatLngConverter.TO_GRID, latitude, longitude)
        Log.e("TEST", "x: ${coord.x} y: ${coord.y}")

        options["serviceKey"] =
            "jbpUUy%2FMmr0n5alZ225PBgJL%2FiRcdbJxJ3%2BxyfBpbCCj3Kt0venEiltIO2xP2duLf4BBqytQ7cPYYGaGaNBUPg%3D%3D"
        options["pageNo"] = "1"
        options["numOfRows"] = "1000"
        options["dataType"] = "JSON"

        var baseDate = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        lateinit var baseTime: String
        if(time.hour % 3 == 2 && time.minute >= 10) {
            baseTime = time.format(DateTimeFormatter.ofPattern("hh")) + "00"
        } else {
            Log.e("TEST", time.hour.toString())
            baseTime = String.format("%02d", ((time.hour / 3 * 3)) - 1) + "00"
        }
        options["base_date"] = baseDate
        options["base_time"] = baseTime
        options["nx"] = coord.x.toInt().toString()
        options["ny"] = coord.y.toInt().toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherApi.getWeather(options)
            if (response.isSuccessful) {
                val jsonObject = JsonParser.parseString(response.body()).asJsonObject
                val responseObject = jsonObject["response"].asJsonObject
                val responseBody = responseObject["body"].asJsonObject
                val itemsArray = responseBody["items"].asJsonObject["item"].asJsonArray

                val weatherInfo = WeatherInfo()
                val list = itemsArray.asList()
                for (weatherString in list) {
                    val weatherDto =
                        Gson().fromJson(weatherString.toString(), WeatherDTO::class.java)

                    when (weatherDto.category) {
                        "TMP" -> weatherInfo.tmp = weatherDto.fcstValue.toDouble()
                        "TMN" -> weatherInfo.minTmp = weatherDto.fcstValue.toDouble()
                        "TMX" -> weatherInfo.maxTmp = weatherDto.fcstValue.toDouble()
                        "PCP" -> weatherInfo.rain = weatherDto.fcstValue
                        "UUU", "VVV" -> weatherInfo.wind = sqrt(
                            weatherInfo.wind.toDouble().pow(2.0) + weatherDto.fcstValue.toDouble()
                                .pow(2.0)
                        ).toInt()

                        "REH" -> weatherInfo.humidity = weatherDto.fcstValue.toInt()
                    }
                }

                _weather.value = weatherInfo
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }
}