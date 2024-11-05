package com.wcd.farm.data.remote

import com.wcd.farm.data.model.RefreshDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.POST

interface AuthApi {
    companion object {
        private const val AUTH_BASE = "auth"
    }
    @POST("$AUTH_BASE/signOut")
    suspend fun signOut(): Response<Any>

    @POST("$AUTH_BASE/refresh")
    suspend fun refresh(): Response<ResponseDTO<RefreshDTO>>
}