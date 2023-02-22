package com.bunbeauty.fooddelivery.data.model.company

import kotlinx.serialization.Serializable

@Serializable
class GetPayment(
    val phoneNumber: String?,
    val cardNumber: String?,
)
