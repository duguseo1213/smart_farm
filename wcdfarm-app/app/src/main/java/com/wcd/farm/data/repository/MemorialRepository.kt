package com.wcd.farm.data.repository

import android.util.Log
import com.wcd.farm.data.model.AnimalDTO
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.data.model.PictureDTO
import com.wcd.farm.data.model.TheftDTO
import com.wcd.farm.data.model.TimeLapseImageDTO
import com.wcd.farm.data.remote.GalleryApi
import com.wcd.farm.data.remote.HarmApi
import com.wcd.farm.data.remote.TimeLapseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class MemorialRepository @Inject constructor(private val galleryApi: GalleryApi, private val timeLapseApi: TimeLapseApi, private val harmApi: HarmApi) {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _datePictureListMap = MutableStateFlow<Map<LocalDate, List<PictureDTO>>>(emptyMap())
    val datePictureListMap = _datePictureListMap.asStateFlow()

    private val _pictureList = MutableStateFlow<List<PictureDTO>>(emptyList())
    val pictureList = _pictureList.asStateFlow()

    private val _harmList = MutableStateFlow<List<HarmDTO>>(emptyList())
    val harmList = _harmList.asStateFlow()

    private val _harmAnimalList = MutableStateFlow<List<AnimalDTO>>(emptyList())
    val harmAnimalList = _harmAnimalList.asStateFlow()

    private val _harmTheftList = MutableStateFlow<List<TheftDTO>>(emptyList())
    val harmTheftList = _harmTheftList.asStateFlow()

    private val _timeLapseImageList = MutableStateFlow<List<TimeLapseImageDTO>>(emptyList())
    val timeLapseImageList = _timeLapseImageList.asStateFlow()

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getAllPictures(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("TEST", "getPictures")
            val response = galleryApi.getPictures(gardenId)
            Log.e("TEST", response.isSuccessful.toString())
            if(response.isSuccessful) {
                /*Log.e("TEST", "success")
                Log.e("TEST", response.body()!!.data.size.toString())*/
                /*val pictureList = response.body()?.data

                if (pictureList != null) {
                    for(picture in pictureList) {
                        Log.e("TEST", picture.toString())
                    }
                }*/
            } else {

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

    fun getTimeLapse(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = timeLapseApi.getTimeLapseList(gardenId)

            if(response.isSuccessful) {
                Log.e("TEST", response.body().toString())
            }
        }
    }

    fun getHarmAnimalList(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = harmApi.getHarmAnimal(gardenId)

            if(response.isSuccessful) {
                val list = response.body()!!.data
                Log.e("TEST", response.body()!!.toString())
                _harmAnimalList.value = list
            }
        }
    }

    fun getHarmTheftList(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = harmApi.getHarmHuman(gardenId)

            if(response.isSuccessful) {
                val list = response.body()!!.data
                Log.e("TEST", response.body()!!.toString())
                _harmTheftList.value = list
            }
        }
    }
}