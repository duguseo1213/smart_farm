package com.wcd.farm.data.remote

import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    companion object {
        private const val USER_BASE = "user"
    }

    @POST("$USER_BASE/set-fcm-token")
    suspend fun setFcmToken(@Query("fcmToken") fcmToken: String): Response<ResponseDTO<Any>>
}