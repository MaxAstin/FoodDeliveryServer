package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.model.company.GetPayment

interface IPaymentService {

    suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment?
}