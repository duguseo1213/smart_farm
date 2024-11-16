package com.wcd.farm.data.remote

import com.wcd.farm.data.model.AnimalDTO
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.data.model.ResponseDTO
import com.wcd.farm.data.model.TheftDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HarmApi {
    companion object {
        private const val HARM_BASE = "harm"
    }

    @GET("$HARM_BASE/get-harm-picture")
    suspend fun getHarmPicture(@Query("gardenId") gardenId: Long): Response<ResponseDTO<List<HarmDTO>>>

    @GET("$HARM_BASE/get-harm-animal")
    suspend fun getHarmAnimal(@Query("gardenId") gardenId: Long): Response<ResponseDTO<List<AnimalDTO>>>

    @GET("$HARM_BASE/get-harm-human")
    suspend fun getHarmHuman(@Query("gardenId") gardenId: Long): Response<ResponseDTO<List<TheftDTO>>>

    @GET("$HARM_BASE/get-harm-video")
    suspend fun getHarmVideo(@Query("harmPictureId") harmPictureId: Long): Response<ResponseDTO<String>>

    @POST("$HARM_BASE/toggle-detection")
    suspend fun toggleDetection(@Query("gardenId") gardenId: Long): Response<ResponseDTO<String>>
}