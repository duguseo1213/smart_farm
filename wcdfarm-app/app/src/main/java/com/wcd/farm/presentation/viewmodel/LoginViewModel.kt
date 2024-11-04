package com.wcd.farm.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import com.wcd.farm.data.remote.LoginApi
import com.wcd.farm.data.remote.ServerClient
import com.wcd.farm.data.remote.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val loginApi = ServerClient.loginApi

    fun login(context: Context, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            loginApi.login("https://k11c104.p.ssafy.io/app")
            /*if(response.isSuccessful) {
                Log.e("TEST", response.body().toString())
            } else {
                Log.e("TEST", response.errorBody()!!.string())
            }*/
        }
        /*UserApiClient.instance.loginWithKakaoTalk(
            context,
        ) { token, error ->
            if (error != null) {
                Log.e("TEST", "로그인 실패 $error")
            } else if (token != null) {
                Log.e("TEST", "로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    Log.e("TEST", user.toString())
                }
                onSuccess()
            }
        }*/
    }
}