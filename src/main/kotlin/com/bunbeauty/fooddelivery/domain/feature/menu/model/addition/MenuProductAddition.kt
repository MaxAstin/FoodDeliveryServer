package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

data class MenuProductAddition(
    val uuid: String,
    val name: String,
    val fullName: String?,
    val isSelected: Boolean,
    val price: Int?,
    val photoLink: String,
    val priority: Int,
    val isVisible: Boolean,
)