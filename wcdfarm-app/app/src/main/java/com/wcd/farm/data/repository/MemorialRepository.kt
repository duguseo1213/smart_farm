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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

class MemorialRepository @Inject constructor(private val galleryApi: GalleryApi, private val timeLapseApi: TimeLapseApi, private val harmApi: HarmApi) {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _datePictureListMap = MutableStateFlow<Map<String, List<PictureDTO>>>(emptyMap())
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

    private val _crtTimeLapseImage = MutableStateFlow<Int?>(null)
    val crtTimeLapseImage = _crtTimeLapseImage.asStateFlow()

    private val _newPicture = MutableStateFlow<String?>(null)
    val newPicture = _newPicture.asStateFlow()

    private val _selectedHarm = MutableStateFlow<HarmDTO?>(null)
    val selectedHarm = _selectedHarm.asStateFlow()

    private val _invasionVideoUrl = MutableStateFlow("")
    val invasionVideoUrl = _invasionVideoUrl.asStateFlow()

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getAllPictures(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = galleryApi.getPictures(gardenId)
            if(response.isSuccessful) {
                Log.e("TEST", "getAllPictures: " + response.body().toString())
                val pictureList = response.body()?.data
1
                if (pictureList != null) {
                    val newMap = mutableMapOf<String, MutableList<PictureDTO>>()
                    for(picture in pictureList) {
                        val time = LocalDateTime.parse(picture.pictureDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        val date = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        if(!newMap.containsKey(date)) newMap[date] = mutableListOf()
                        newMap[date]?.add(picture)
                    }
                    _datePictureListMap.value = newMap
                }
            }
        }
    }

    fun updatePictures(date: String) {
        if(datePictureListMap.value.containsKey(date)) {
            _pictureList.value = datePictureListMap.value[date]!!
        }
    }

    fun getPicturesOnDate(gardenId: Long, createdDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("TEST", "getPicturesOnDate: $gardenId, $createdDate")
            val response = galleryApi.getPictureOnDate(gardenId, createdDate)

            if(response.isSuccessful) {
                Log.e("TEST", "getPicturesOnDate: " + response.body().toString())
                val pictureList = response.body()?.data
                if (pictureList != null) {
                    Log.e("TEST", "isNotNull")
                    //_pictureList.value = pictureList
                }
            }
        }
    }

    fun getTimeLapse(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = timeLapseApi.getTimeLapseList(gardenId)

            if(response.isSuccessful) {
                _timeLapseImageList.value = response.body()!!.data
                Log.e("TEST", "timeLapse: " + response.body().toString())
            } else {
                Log.e("TEST", "errorBody: " + response.errorBody()!!.string())
            }
        }
    }

    fun getHarmAnimalList(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = harmApi.getHarmAnimal(gardenId)

            if(response.isSuccessful) {
                val list = response.body()?.data
                Log.e("TEST", response.body()!!.toString())
                if(list != null) {
                    _harmAnimalList.value = list
                }
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

    fun setNewPicture(imageUrl: String?) {
        _newPicture.value = imageUrl
    }

    fun addNewPicture(imageUrl: String, description: String, gardenId: Long) {
        val body = mutableMapOf<String, String>()
        body["image"] = imageUrl
        body["description"] = description
        body["gardenId"] = gardenId.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val response = galleryApi.addPicture(body)

            if(response.isSuccessful) {
                Log.e("TEST", response.body()!!.data)
                setNewPicture(null)
            }
        }
    }

    fun getInvasionVideoUrl(harmPictureId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = harmApi.getHarmVideo(harmPictureId)

            if(response.isSuccessful) {
                val videoUrl = response.body()?.data

                if (videoUrl != null) {
                    _invasionVideoUrl.value = videoUrl
                }
            }
        }
    }

    fun selectHarm(harm: HarmDTO) {
        _selectedHarm.value = harm
    }

    fun setTimeLapseImage(index: Int?) {
        _crtTimeLapseImage.value = index
    }

    fun clearPictures() {
        _datePictureListMap.value = mutableMapOf()
    }
}