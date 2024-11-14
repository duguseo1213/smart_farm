package com.wcd.farm.data.remote

import com.wcd.farm.data.model.PictureDTO
import com.wcd.farm.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface GalleryApi {
    companion object {
        private const val GALLERY_BASE = "gallery"
    }

    @GET("$GALLERY_BASE/get-pictures")
    suspend fun getPictures(@Query("gardenId") gardenId: Long): Response<ResponseDTO<List<PictureDTO>>>

    @GET("$GALLERY_BASE/get-pictures-on-date")
    suspend fun getPictureOnDate(
        @Query("gardenId") gardenId: Long,
        @Query("createdDate") createdDate: LocalDate
    ): Response<ResponseDTO<List<PictureDTO>>>
}