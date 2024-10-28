package com.wcd.farm.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.wcd.farm.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    val weather = weatherRepository.weather

    fun getCrtWeather() {
        val time = LocalDateTime.now(ZoneId.systemDefault())
        weatherRepository.getCrtWeather(0, 37.566, 126.978, time)
    }
}