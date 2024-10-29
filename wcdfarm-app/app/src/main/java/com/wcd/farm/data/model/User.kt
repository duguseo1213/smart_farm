package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Timestamp

data class User(
    val userId: BigInteger,
    val username: String,
    val gender: String,
    val age: String,
    val name: String,
    val nickname: String,
    val email: String,
    val roleType: String,
    val providedId: String,
    val profileImage: String,
    val fcmToken: String,
    val createdAt: Timestamp
)
