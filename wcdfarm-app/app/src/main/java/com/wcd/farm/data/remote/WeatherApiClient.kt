package com.wcd.farm.data.remote

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object WeatherApiClient {
    private const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    private val gson = GsonBuilder().setLenient().create()

    private val retryInterceptor: Interceptor = object : Interceptor {
        private val maxRetry = 3
        private var retryCount = 0

        @Throws(IOException::class)
        override fun intercept(chain: Chain): Response {
            val request = chain.request()
            var response: Response? = null
            var responseOK = false

            while (!responseOK && retryCount < maxRetry) {
                try {
                    response = chain.proceed(request)
                    responseOK = response.isSuccessful
                } catch (e: Exception) {
                    retryCount++
                    if (retryCount >= maxRetry) {
                        throw e
                    }
                }
            }
            return response!!
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(retryInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

}