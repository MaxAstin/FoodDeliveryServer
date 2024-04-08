package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PostAdditionGroup(
    val name: String,
    val singleChoice: Boolean,
    val priority: Int,
    val isVisible: Boolean,
)