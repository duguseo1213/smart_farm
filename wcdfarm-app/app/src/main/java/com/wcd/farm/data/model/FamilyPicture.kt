package com.wcd.farm.data.model

import java.math.BigInteger
import java.sql.Date

data class FamilyPicture(
    val familyPictureId: BigInteger,
    val gardenId: BigInteger,
    val familyImage: String,
    val createdDate: Date
)
