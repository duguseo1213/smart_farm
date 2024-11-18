package com.wcd.farm.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.wcd.farm.data.remote.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServerRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
) {
    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

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

    fun setLoginState(state: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("loginState", state)
        editor.apply()
        _loginState.value = state
    }

    fun getLoginState() {
        val loginState = sharedPreferences.getBoolean("loginState", false)
        _loginState.value = loginState
    }
}