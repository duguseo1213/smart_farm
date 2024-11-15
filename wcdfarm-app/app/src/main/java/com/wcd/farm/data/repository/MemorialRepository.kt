package com.wcd.farm.data.repository

import android.util.Log
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.data.model.PictureDTO
import com.wcd.farm.data.remote.GalleryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class MemorialRepository @Inject constructor(private val galleryApi: GalleryApi) {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _datePictureListMap = MutableStateFlow<Map<LocalDate, List<PictureDTO>>>(emptyMap())
    val datePictureListMap = _datePictureListMap.asStateFlow()

    private val _pictureList = MutableStateFlow<List<PictureDTO>>(emptyList())
    val pictureList = _pictureList.asStateFlow()

    private val _harmList = MutableStateFlow<List<HarmDTO>>(emptyList())
    val harmList = _harmList.asStateFlow()

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getAllPictures(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = galleryApi.getPictures(gardenId)

            if(response.isSuccessful) {
                val pictureList = response.body()?.data

                if (pictureList != null) {
                    for(picture in pictureList) {
                        Log.e("TEST", picture.toString())
                    }
                }
            }
        }
    }

    fun getPicturesOnDate(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = galleryApi.getPictureOnDate(gardenId, selectedDate.value)

            if(response.isSuccessful) {
                val pictureList = response.body()?.data
            }
        }
    }
}