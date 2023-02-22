package com.bunbeauty.fooddelivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PatchClientUserSettings(
    val email: String? = null,
    val isActive: Boolean? = null,
)