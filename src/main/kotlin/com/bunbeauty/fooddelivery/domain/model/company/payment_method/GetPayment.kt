package com.bunbeauty.fooddelivery.domain.model.company.payment_method

import kotlinx.serialization.Serializable

@Serializable
class GetPayment(
    val phoneNumber: String?,
    val cardNumber: String?
)
