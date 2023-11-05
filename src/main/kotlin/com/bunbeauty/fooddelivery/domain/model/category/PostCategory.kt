package com.bunbeauty.fooddelivery.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
class PostCategory(
    val name: String,
    val priority: Int
)
