package com.wcd.farm.data.repository

import android.util.Log
import com.wcd.farm.data.model.CropDTO
import com.wcd.farm.data.model.GardenDTO
import com.wcd.farm.data.model.GardenState
import com.wcd.farm.data.remote.CropApi
import com.wcd.farm.data.remote.GardenApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GardenRepository @Inject constructor(
    private val gardenApi: GardenApi,
    private val cropApi: CropApi
) {
    private val _gardenList = MutableStateFlow<List<GardenDTO>>(emptyList())
    val gardenList = _gardenList.asStateFlow()

    private val _crtGarden = MutableStateFlow<Int?>(null)
    val crtGarden = _crtGarden.asStateFlow()

    private val _crtWeatherGardenIndex = MutableStateFlow<Int?>(null)
    val crtWeatherGardenIndex = _crtWeatherGardenIndex.asStateFlow()

    private val _crtGardenState = MutableStateFlow<GardenState?>(null)
    val crtGardenState = _crtGardenState.asStateFlow()

    private val _gardenStreamKeyMap = MutableStateFlow<Map<Long, String>>(emptyMap())
    val gardenStreamKeyMap = _gardenStreamKeyMap.asStateFlow()

    private val _selectedCrop = MutableStateFlow<CropDTO>(CropDTO("", 0))
    val selectedCrop = _selectedCrop.asStateFlow()

    private val _gardenCropList = MutableStateFlow<List<CropDTO>>(emptyList())
    val gardenCropList = _gardenCropList.asStateFlow()

    private val _recommendCropList = MutableStateFlow<List<String>>(emptyList())
    val recommendCropList = _recommendCropList.asStateFlow()

    fun requestRemoteWater() {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, String>()
            body["gardenId"] = gardenList.value[crtGarden.value!!].gardenId.toString()
            val response = gardenApi.postRemoteWater(body)

            if (response.isSuccessful) {
                Log.e("TEST", response.body().toString())
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }

    fun requestTakePicture() {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, String>()
            body["gardenId"] = gardenList.value[crtGarden.value!!].gardenId.toString()
            val response = gardenApi.postTakePicture(body)

            if (response.isSuccessful) {
                Log.e("TEST", response.body().toString())
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }

    fun getGardenList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = gardenApi.getGardens()

            if (response.isSuccessful) {
                val gardenList = response.body()?.data
                Log.e("TEST", "getGardenList: " + response.body().toString())

                if (!gardenList.isNullOrEmpty()) {

                    _gardenList.value = gardenList
                    if(_crtGarden.value == null) _crtGarden.value = 0
                }
            }
        }
    }

    fun getGardenState(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = gardenApi.getGardenData(gardenId)

            if (response.isSuccessful) {
                val gardenState = response.body()?.data
                Log.e("TEST", "getGardenState: " + response.body().toString())
                _crtGardenState.value = gardenState
            }
        }
    }

    fun getGardenCrops(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = cropApi.getCrops(gardenId)

            if (response.isSuccessful) {
                Log.e("TEST", response.body().toString())
                val gardenCropList = response.body()?.data

                if (!gardenCropList.isNullOrEmpty()) {
                    selectCrop(gardenCropList[0])
                    _gardenCropList.value = gardenCropList
                }


            }
        }
    }

    fun getRecommendCrops(cropName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("TEST", "recommend")
            val response = cropApi.getRecommendCrop(cropName)

            if(response.isSuccessful) {
                Log.e("TEST", response.body().toString())
                _recommendCropList.value = response.body()!!.data
                for(crop in response.body()!!.data) {
                    Log.e("TEST", "recommended: $crop")
                }
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }

    fun getStreamKeys() {
        CoroutineScope(Dispatchers.IO).launch {
            val gardenStreamKeyMap = mutableMapOf<Long, String>()
            for (garden in gardenList.value) {
                val response = gardenApi.getStreamKey(garden.gardenId)

                if (response.isSuccessful) {
                    val streamKey = response.body()!!.data
                    Log.e("TEST", "${garden.gardenId} $streamKey")
                    gardenStreamKeyMap[garden.gardenId] = streamKey
                }
            }

            _gardenStreamKeyMap.value = gardenStreamKeyMap
        }
    }

    fun selectCrop(crop: CropDTO) {
        _selectedCrop.value = crop
    }

    fun addCrop(gardenId: Long, cropName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = cropApi.addCrop(gardenId, cropName)
            if (response.isSuccessful) {
                getGardenCrops(gardenId)

                Log.e("TEST", response.body().toString())
            }
        }
    }

    fun deleteCrop(gardenId: Long, cropName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = cropApi.deleteCrop(gardenId, cropName)
            if (response.isSuccessful) {
                getGardenCrops(gardenId)

                Log.e("TEST", response.body().toString())
            }
        }
    }

    fun addGarden(gardenId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, Long>()
            body["gardenId"] = gardenId

            val response = gardenApi.addUserToGarden(body)

            if(response.isSuccessful) {
                getGardenList()
                Log.e("TEST", "addGarden: " + response.body().toString())
            } else {
                Log.e("TEST", "error: " + response.errorBody()!!.string())
            }
        }
    }

    fun changeGardenName(gardenId: Long, gardenName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("TEST", gardenId.toString())
            Log.e("TEST", gardenName)
            val response = gardenApi.changeGardenName(gardenName, gardenId)

            if(response.isSuccessful) {
                getGardenList()
                Log.e("TEST", response.body().toString())
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }

    fun setCrtGarden(index: Int) {
        _crtGarden.value = index
    }

    fun setCrtWeatherGardenIndex(index: Int) {
        _crtWeatherGardenIndex.value = index
    }
}