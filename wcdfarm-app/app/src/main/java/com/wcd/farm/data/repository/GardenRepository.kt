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
    private val _gardenList = MutableStateFlow<List<Long>>(emptyList())
    val gardenList = _gardenList.asStateFlow()

    private val _crtGarden = MutableStateFlow(1L)
    val crtGarden = _crtGarden.asStateFlow()

    fun requestRemoteWater() {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mutableMapOf<String, String>()
            body["gardenId"] = crtGarden.value.toString()
            val response = gardenApi.postRemoteWater(body)

            if(response.isSuccessful) {
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

            if(response.isSuccessful) {
                Log.e("TEST", response.body().toString())
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }
}