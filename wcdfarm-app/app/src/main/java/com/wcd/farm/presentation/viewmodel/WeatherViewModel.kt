package com.wcd.farm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.wcd.farm.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    val weather = weatherRepository.weather
    val forecastWeather = weatherRepository.forecastWeather

    fun getLiveWeather(latitude: Double, longitude: Double) {
        val time = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        weatherRepository.getLiveWeather(latitude, longitude, time)
    }

    fun getNearForecastWeather(latitude: Double, longitude: Double) {
        val time = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        weatherRepository.getNearForecastWeather(latitude, longitude, time)
    }

    fun getForecastWeather() {
        val time = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        weatherRepository.getForecastWeather(time)
    }
}