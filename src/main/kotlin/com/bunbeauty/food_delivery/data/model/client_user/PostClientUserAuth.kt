package com.bunbeauty.food_delivery.data.model.client_user

import kotlinx.serialization.Serializable

@Serializable
data class PostClientUserAuth(
    val firebaseUuid: String,
    val phoneNumber: String,
    val companyUuid: String
)
