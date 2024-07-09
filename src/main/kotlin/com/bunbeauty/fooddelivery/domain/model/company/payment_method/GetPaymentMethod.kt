package com.bunbeauty.fooddelivery.domain.model.company.payment_method

import kotlinx.serialization.Serializable

@Serializable
class GetPaymentMethod(
    val uuid: String,
    val name: String,
    val value: String?,
    val valueToCopy: String?
)
