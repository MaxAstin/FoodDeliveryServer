package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class GetMenuProductAddition(
    val uuid: String,
    val name: String,
    val fullName: String?,
    val isSelected: Boolean,
    val price: Int?,
    val photoLink: String,
    val priority: Int,
    val isVisible: Boolean,
)