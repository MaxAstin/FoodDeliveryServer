package com.bunbeauty.fooddelivery.data.model.category

import kotlinx.serialization.Serializable

@Serializable
data class PatchCategory(
    val name: String?,
    val priority: Int?
)
