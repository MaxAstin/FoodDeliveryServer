package com.bunbeauty.fooddelivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
data class GetPayment(
    val phoneNumber: String?,
    val cardNumber: String?,
)
