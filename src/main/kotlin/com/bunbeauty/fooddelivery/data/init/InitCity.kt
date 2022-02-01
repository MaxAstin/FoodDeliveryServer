package com.bunbeauty.fooddelivery.data.init

data class InitCity(
    val name: String,
    val timeZone: String,
    val isVisible: Boolean,
    val cafeList: List<InitCafe>,
    val userList: List<InitUser>,
)