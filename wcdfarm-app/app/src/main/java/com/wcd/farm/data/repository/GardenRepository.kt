package com.wcd.farm.data.repository

import android.util.Log
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
    private val _gardenList = MutableStateFlow<List<GardenDTO>>(listOf(GardenDTO(2, "효린이네", "광주광역시 광산구 장덕동 982-10", "2024-10-24"))/*emptyList()*/)
    val gardenList = _gardenList.asStateFlow()

    private val _crtGarden = MutableStateFlow<GardenDTO?>(GardenDTO(2, "효린이네", "광주광역시 광산구 장덕동 982-10", "2024-10-24"))
    val crtGarden = _crtGarden.asStateFlow()

    private val _crtGardenState = MutableStateFlow<GardenState?>(null)
    val crtGardenState = _crtGardenState.asStateFlow()

    private val _gardenStreamKeyMap = MutableStateFlow<Map<Long, String>>(emptyMap())
    val gardenStreamKeyMap = _gardenStreamKeyMap.asStateFlow()

    private val _selectedCrop = MutableStateFlow("")
    val selectedCrop = _selectedCrop.asStateFlow()

    private val _gardenCropList = MutableStateFlow<List<String>>(emptyList())
    val gardenCropList = _gardenCropList.asStateFlow()

    private val _recommendCropList = MutableStateFlow<List<String>>(emptyList())
    val recommendCropList = _recommendCropList.asStateFlow()

    fun requestRemoteWater() {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, String>()
            body["gardenId"] = crtGarden.value.toString()
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
            body["gardenId"] = crtGarden.value.toString()
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

                if (gardenList != null) {
                    _gardenList.value = gardenList
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
                val gardenCropList = response.body()!!.data

                if (gardenCropList.isNotEmpty()) {
                    selectCrop(gardenCropList[0])
                }

                _gardenCropList.value = gardenCropList
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

    fun selectCrop(cropName: String) {
        _selectedCrop.value = cropName
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

    fun addGarden() {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, String>()

            val response = gardenApi.postAddGarden(body)
        }
    }
}