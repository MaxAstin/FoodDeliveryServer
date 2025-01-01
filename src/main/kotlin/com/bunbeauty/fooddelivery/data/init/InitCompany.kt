package com.bunbeauty.fooddelivery.data.init

class InitCompany(
    val name: String,
    val forFreeDelivery: Int,
    val deliveryCost: Int,
    val forceUpdateVersion: Int,
    val cityList: List<InitCity>,
    val categoryList: List<InitCategory>,
    val menuList: List<InitMenuProduct>,
)