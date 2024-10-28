package com.wcd.farm.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface WeatherApi {
    @Headers("content-type: application/json; charset=utf8")
    @GET("getVilageFcst")
    suspend fun getWeather(@QueryMap(encoded = true) options: Map<String, String>): Response<String>
}