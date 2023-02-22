package com.bunbeauty.fooddelivery.data.model.category

import kotlinx.serialization.Serializable

@Serializable
class PatchCategory(
    val name: String? = null,
    val priority: Int? = null,
)