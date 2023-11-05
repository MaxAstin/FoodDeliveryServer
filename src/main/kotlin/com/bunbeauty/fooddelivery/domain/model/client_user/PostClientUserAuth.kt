package com.bunbeauty.fooddelivery.domain.model.client_user

import kotlinx.serialization.Serializable

@Serializable
class PostClientUserAuth(
    val firebaseUuid: String,
    val phoneNumber: String,
    val companyUuid: String
)
