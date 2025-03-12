package com.bunbeauty.fooddelivery.domain.feature.clientuser.model

class ClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val isActive: Boolean
)
