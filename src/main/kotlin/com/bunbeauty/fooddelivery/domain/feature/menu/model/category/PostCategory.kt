package com.bunbeauty.fooddelivery.domain.feature.menu.model.category

import kotlinx.serialization.Serializable

@Serializable
class PostCategory(
    val name: String,
    val priority: Int
)
