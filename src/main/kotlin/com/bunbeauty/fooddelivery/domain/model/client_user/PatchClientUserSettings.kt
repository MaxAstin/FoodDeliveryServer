package com.bunbeauty.fooddelivery.domain.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PatchClientUserSettings(
    val email: String? = null,
    val isActive: Boolean? = null
)
