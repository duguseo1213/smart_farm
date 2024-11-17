package com.wcd.farm.data.remote

import com.wcd.farm.data.model.DeviceLiveDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface DeviceApi {
    companion object {
        private const val DEVICE_BASE = "device"
    }
    @GET("$DEVICE_BASE/get-stream-key")
    suspend fun getStreamKey(): Response<ResponseDTO<DeviceLiveDTO>>

    @PATCH("$DEVICE_BASE/sync-detection-status")
    suspend fun syncDetectionStatus(@Body body: Map<String, String>): Response<ResponseDTO<String>>
}