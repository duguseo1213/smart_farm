package com.wcd.farm.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {
    @GET("authorization/kakao")
    fun login(@Query("redirect_uri") redirectUri: String)
}