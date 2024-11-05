package com.wcd.farm.data.repository

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ServerRepository @Inject constructor() {
    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()
    private val _refreshToken = MutableStateFlow("")
    val refreshToken = _refreshToken.asStateFlow()

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }

    fun setRefreshToken(refreshToken: String) {
        _refreshToken.value = refreshToken
    }
}