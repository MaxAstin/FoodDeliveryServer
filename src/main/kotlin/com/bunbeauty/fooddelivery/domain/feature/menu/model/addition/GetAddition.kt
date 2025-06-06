package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class GetAddition(
    val uuid: String,
    val name: String,
    val fullName: String?,
    val price: Int?,
    val photoLink: String,
    val tag: String?,
    val priority: Int,
    val isVisible: Boolean
)
