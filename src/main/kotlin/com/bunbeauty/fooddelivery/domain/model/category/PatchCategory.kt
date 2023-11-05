package com.bunbeauty.fooddelivery.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
class PatchCategory(
    val name: String? = null,
    val priority: Int? = null,
)