package com.bunbeauty.fooddelivery.domain.feature.company.mapper

import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment

val mapPayment: Payment.() -> GetPayment = {
    GetPayment(
        phoneNumber = phoneNumber,
        cardNumber = cardNumber
    )
}
