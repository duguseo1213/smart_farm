package com.wcd.farm.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.wcd.farm.data.remote.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServerRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
) {
    fun setAccessToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.apply()
    }

    fun setRefreshToken(refreshToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("refreshToken", refreshToken)
        editor.apply()
    }

    fun requestRefreshToken() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authApi.refresh()
            if (response.isSuccessful) {
                val responseDTO = response.body()!!.data
                setAccessToken(responseDTO.accessToken)
                setRefreshToken(responseDTO.refreshToken)
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }
        }
    }
}