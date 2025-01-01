package com.bunbeauty.fooddelivery.data.init

class InitMenuProduct(
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val isVisible: Boolean,
    val categories: List<String>,
)