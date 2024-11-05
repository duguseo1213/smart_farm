package com.wcd.farm.data.model

data class ResponseDTO<T>(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: T
)
