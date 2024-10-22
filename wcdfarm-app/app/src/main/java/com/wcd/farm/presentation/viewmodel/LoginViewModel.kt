package com.wcd.farm.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel

class LoginViewModel : ViewModel() {
    fun login(context: Context, onSuccess: () -> Unit) {

        /*UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("TEST", "연결 끊기 실패", error)
            } else {
                Log.i("TEST", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }*/

        /*UserApiClient.instance.loginWithKakaoAccount(context, prompts = listOf(Prompt.LOGIN)) { token, error ->
            Log.e("TEST", "TEST")
            if (error != null) {
                Log.e("TEST", "로그인 실패 $error")
            } else if (token != null) {
                Log.e("TEST", "로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    Log.e("TEST", user.toString())
                }

                onSuccess()
            } else {
                Log.e("TEST", "TEST")
            }
        }*/

        UserApiClient.instance.loginWithKakaoTalk(
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
        }
    }
}