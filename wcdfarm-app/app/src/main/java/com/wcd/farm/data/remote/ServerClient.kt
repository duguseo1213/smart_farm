package com.wcd.farm.data.remote

import com.google.gson.GsonBuilder
import com.wcd.farm.data.repository.ServerRepository
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ServerClient {
    private const val BASE_URL = "https://k11c104.p.ssafy.io/"
    private lateinit var repository: ServerRepository

    fun init(repository: ServerRepository) {
        this.repository = repository
    }

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
                    response?.close()
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
        .cookieJar(object: CookieJar {
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val refreshToken = repository.refreshToken.value // 실제로 저장된 refreshToken 값 사용

                val cookie = Cookie.Builder()
                    .domain(url.host)
                    .path(url.encodedPath)
                    .name("refreshToken")
                    .value(refreshToken)
                    .httpOnly()
                    .secure()
                    .build()

                return listOf(cookie)
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) { }
        })
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${repository.accessToken.value}")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(retryInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}