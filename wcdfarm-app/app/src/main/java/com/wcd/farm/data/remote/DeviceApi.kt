package com.wcd.farm.data.remote

import com.wcd.farm.data.model.DeviceLiveDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.POST

interface DeviceApi {
    companion object {
        private const val DEVICE_BASE = "device"
    }
    @POST("$DEVICE_BASE/register")
    suspend fun geStreamKey(): Response<ResponseDTO<DeviceLiveDTO>>
}