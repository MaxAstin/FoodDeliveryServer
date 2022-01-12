package com.bunbeauty.fooddelivery.data.init

data class InitCity(
    val name: String,
    val offset: Int,
    val isVisible: Boolean,
    val cafeList: List<InitCafe>,
    val userList: List<InitUser>,
)