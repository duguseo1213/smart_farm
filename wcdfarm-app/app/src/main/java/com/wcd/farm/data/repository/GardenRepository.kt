package com.wcd.farm.data.repository

import android.util.Log
import com.wcd.farm.data.remote.GardenApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GardenRepository @Inject constructor(private val gardenApi: GardenApi) {
    private val _gardenList = MutableStateFlow<List<Long>>(listOf(2L))
    val gardenList = _gardenList.asStateFlow()

    private val _crtGarden = MutableStateFlow(2L)
    val crtGarden = _crtGarden.asStateFlow()

    private val _gardenStreamKeyMap = MutableStateFlow<Map<Long, String>>(emptyMap())
    val gardenStreamKeyMap = _gardenStreamKeyMap.asStateFlow()

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

    fun getStreamKeys() {
        CoroutineScope(Dispatchers.IO).launch {
            val gardenStreamKeyMap = mutableMapOf<Long, String>()
            for (gardenId in gardenList.value) {
                val response = gardenApi.getStreamKey(crtGarden.value)

                if (response.isSuccessful) {
                    val streamKey = response.body()!!.data
                    Log.e("TEST", "$gardenId $streamKey")
                    gardenStreamKeyMap[gardenId] = streamKey
                }
            }

            _gardenStreamKeyMap.value = gardenStreamKeyMap
        }
    }
}