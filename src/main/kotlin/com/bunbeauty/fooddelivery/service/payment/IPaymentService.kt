package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment

interface IPaymentService {

    suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment
}