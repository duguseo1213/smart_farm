package com.wcd.farm.data.remote

import com.wcd.farm.data.model.CropDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CropApi {
    companion object {
        private const val CROP_BASE = "crop"
    }

    @GET("$CROP_BASE/get-crops/{gardenId}")
    suspend fun getCrops(@Path("gardenId") gardenId: Long): Response<ResponseDTO<List<CropDTO>>>

    @POST("$CROP_BASE/recommand-crop")
    suspend fun getRecommendCrop(@Query("cropName") cropName: String): Response<ResponseDTO<List<String>>>

    @POST("$CROP_BASE/add-crop")
    suspend fun addCrop(@Query("gardenId") gardenId: Long, @Query("crops") cropName: String): Response<ResponseDTO<Any>>

    @POST("$CROP_BASE/delete-crop")
    suspend fun deleteCrop(@Query("gardenId") gardenId: Long, @Query("crops") cropName: String): Response<ResponseDTO<Any>>

}