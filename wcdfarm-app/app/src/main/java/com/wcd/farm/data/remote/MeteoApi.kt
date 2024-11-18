package com.wcd.farm.data.remote

import com.wcd.farm.data.AreaCodeDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoApi {
    companion object {
        private const val METEO_BASE = "meteo"
    }

    @GET("$METEO_BASE/conversionareacode")
    suspend fun getAreaCode(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Response<ResponseDTO<AreaCodeDTO>>
}