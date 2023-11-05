package com.bunbeauty.fooddelivery.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
class GetCategory(
    val uuid: String,
    val name: String,
    val priority: Int,
)
