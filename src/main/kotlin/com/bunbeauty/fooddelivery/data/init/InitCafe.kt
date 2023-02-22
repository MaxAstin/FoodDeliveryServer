package com.bunbeauty.fooddelivery.data.init

class InitCafe(
    val fromTime: Int,
    val toTime: Int,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val isVisible: Boolean,
    val streetList: List<String>,
)