package com.bunbeauty.fooddelivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
data class GetForceUpdateVersion(
    val version: Int
)
