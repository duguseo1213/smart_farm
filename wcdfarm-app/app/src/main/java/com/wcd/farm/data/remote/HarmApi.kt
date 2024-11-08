package com.wcd.farm.data.remote

import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface HarmApi {
    companion object {
        private const val HARM_BASE = "harm"
    }

    @GET("$HARM_BASE/get-harm-picture")
    suspend fun getHarmPicture(): Response<ResponseDTO<HarmDTO>>

    @GET("$HARM_BASE/get-harm-video")
    suspend fun getHarmVideo(): Response<ResponseDTO<String>> // url
}