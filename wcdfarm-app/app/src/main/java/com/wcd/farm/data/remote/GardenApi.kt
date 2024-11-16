package com.wcd.farm.data.remote

import com.wcd.farm.data.model.GardenDTO
import com.wcd.farm.data.model.GardenState
import com.wcd.farm.data.model.GardenUserDTO
import com.wcd.farm.data.model.PlantDiseaseDTO
import com.wcd.farm.data.model.ResponseDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface GardenApi {
    companion object {
        private const val GARDEN_BASE = "garden"
        private const val DEVICE_BASE = "device"
    }

    @GET("$GARDEN_BASE/get-gardens")
    suspend fun getGardens(): Response<ResponseDTO<List<GardenDTO>>>

    @GET("$GARDEN_BASE/get-gardens-users/{gardenId}")
    suspend fun getGardensUsers(@Path("gardenId") gardenId: Long): Response<ResponseDTO<List<GardenUserDTO>>>

    @GET("$GARDEN_BASE/get-garden-data")
    suspend fun getGardenData(@Query("gardenId") gardenId: Long): Response<ResponseDTO<GardenState>>

    @POST("$GARDEN_BASE/take-picture")
    suspend fun postTakePicture(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @POST("$GARDEN_BASE/remote-water")
    suspend fun postRemoteWater(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @Multipart
    @POST("$GARDEN_BASE/plant-disease-detection")
    suspend fun postDiseaseDetection(@Part file: MultipartBody.Part): Response<ResponseDTO<PlantDiseaseDTO>>

    @POST("$GARDEN_BASE/add-user-to-garden")
    suspend fun postAddUserToGarden(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @POST("$GARDEN_BASE/add-garden")
    suspend fun postAddGarden(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @GET("$DEVICE_BASE/get-stream-key")
    suspend fun getStreamKey(@Query("gardenId") gardenId: Long): Response<ResponseDTO<String>>
}