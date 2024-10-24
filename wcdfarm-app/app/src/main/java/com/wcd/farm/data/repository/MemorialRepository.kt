package com.wcd.farm.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

class MemorialRepository @Inject constructor() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }
}