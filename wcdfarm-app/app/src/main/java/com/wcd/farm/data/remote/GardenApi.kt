package com.wcd.farm.data.remote

import com.wcd.farm.data.model.GardenDTO
import com.wcd.farm.data.model.GardenUserDTO
import com.wcd.farm.data.model.PlantDiseaseDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GardenApi {
    companion object {
        private const val GARDEN_BASE = "garden"
        private const val DEVICE_BASE = "device"
    }

    @GET("$GARDEN_BASE/get-gardens/{username}")
    suspend fun getGardens(@Path("username") username: String): Response<ResponseDTO<GardenDTO>>

    @GET("$GARDEN_BASE/get-gardens-users/{gardenId}")
    suspend fun getGardensUsers(@Path("gardenId") gardenId: Long): Response<ResponseDTO<List<GardenUserDTO>>>

    @POST("$GARDEN_BASE/take-picture")
    suspend fun postTakePicture(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @POST("$GARDEN_BASE/remote-water")
    suspend fun postRemoteWater(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @POST("$GARDEN_BASE/plant-disease-detection")
    suspend fun postDiseaseDetection(@Query("file") image: String): Response<ResponseDTO<PlantDiseaseDTO>>

    @POST("$GARDEN_BASE/add-user-to-garden")
    suspend fun postAddUserToGarden(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @POST("$GARDEN_BASE/add-garden")
    suspend fun postAddGarden(@Body body: Map<String, String>): Response<ResponseDTO<String>>

    @GET("$DEVICE_BASE/get-stream-key")
    suspend fun getStreamKey(@Query("gardenId") gardenId: Long): Response<ResponseDTO<String>>
}