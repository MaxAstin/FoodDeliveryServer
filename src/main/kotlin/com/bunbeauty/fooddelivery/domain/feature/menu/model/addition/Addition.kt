package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

data class Addition(
    val uuid: String,
    val name: String,
    val isSelected: Boolean,
    val price: Int?,
    val photoLink: String,
    val isVisible: Boolean,
)