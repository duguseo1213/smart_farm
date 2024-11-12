package com.wcd.farm.data.remote

import android.content.Context
import android.content.SharedPreferences
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
import javax.inject.Inject

object ServerClient {
    private const val BASE_URL = "https://k11c104.p.ssafy.io/api/v1/"

    lateinit var sharedPreferences: SharedPreferences

    fun init(applicationContext: Context) {
        sharedPreferences = applicationContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
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
                val refreshToken = sharedPreferences.getString("refreshToken", "")
                val cookie = Cookie.Builder()
                    .domain(url.host)
                    .path(url.encodedPath)
                    .name("refreshToken")
                    .value(refreshToken!!)
                    .httpOnly()
                    .secure()
                    .build()

                return listOf(cookie)
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) { }
        })
        .addInterceptor { chain ->
            val accessToken = sharedPreferences.getString("accessToken", "")
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
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

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val gardenApi: GardenApi by lazy {
        retrofit.create(GardenApi::class.java)
    }

    val HarmApi: HarmApi by lazy {
        retrofit.create(HarmApi::class.java)
    }

    val deviceApi: DeviceApi by lazy {
        retrofit.create(DeviceApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}