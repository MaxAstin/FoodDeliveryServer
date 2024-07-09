package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PatchAddition(
    val name: String? = null,
    val fullName: String? = null,
    val price: Int? = null,
    val photoLink: String? = null,
    val priority: Int? = null,
    val isVisible: Boolean? = null,
)