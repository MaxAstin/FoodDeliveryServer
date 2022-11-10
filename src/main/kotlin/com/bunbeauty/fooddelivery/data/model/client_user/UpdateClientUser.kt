package com.bunbeauty.fooddelivery.data.model.client_user

import java.util.*

data class UpdateClientUser(
    val uuid: UUID,
    val email: String?,
    val isActive: Boolean?,
)
