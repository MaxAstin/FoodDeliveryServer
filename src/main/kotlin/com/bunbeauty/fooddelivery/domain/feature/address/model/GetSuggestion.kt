package com.bunbeauty.fooddelivery.domain.feature.address.model

import kotlinx.serialization.Serializable

@Serializable
class GetSuggestion(
    val fiasId: String,
    val street: String,
)