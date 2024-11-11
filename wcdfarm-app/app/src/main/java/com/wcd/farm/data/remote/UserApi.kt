package com.wcd.farm.data.remote

import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.POST

interface UserApi {
    companion object {
        private const val USER_BASE = "user"
    }

    @POST("$USER_BASE/set-fcm-token")
    suspend fun setFcmToken(): Response<ResponseDTO<Any>>
}