package com.wcd.farm.data.remote

import com.wcd.farm.data.model.ResponseDTO
import com.wcd.farm.data.model.TimeLapseDTO
import retrofit2.Response
import retrofit2.http.GET

interface TimeLapseApi {
    companion object {
        private const val TIMELAPSE_BASE = "timeLapse"
    }

    @GET("$TIMELAPSE_BASE/get-list")
    suspend fun getTimeLapseList(): Response<ResponseDTO<List<TimeLapseDTO>>>
}