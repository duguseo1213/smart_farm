package com.wcd.farm.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("VilageFcstInfoService_2.0/getUltraSrtNcst")
    suspend fun getLiveWeather(@QueryMap(encoded = true) options: Map<String, String>): Response<String>

    @GET("VilageFcstInfoService_2.0/getVilageFcst")
    suspend fun getNearWeather(@QueryMap(encoded = true) options: Map<String, String>): Response<String>

    @GET("MidFcstInfoService/getMidLandFcst")
    suspend fun getForecastWeather(@QueryMap(encoded = true) options: Map<String, String>): Response<String>

    @GET("MidFcstInfoService/getMidTa")
    suspend fun getForecastTmp(@QueryMap(encoded = true) options: Map<String, String>): Response<String>


}