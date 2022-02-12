package com.bunbeauty.fooddelivery.data.init

data class InitCafe(
    val fromTime: Int,
    val toTime: Int,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val isVisible: Boolean,
    val streetList: List<String>,
)