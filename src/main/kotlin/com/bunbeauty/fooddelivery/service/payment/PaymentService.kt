package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetPayment
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository

class PaymentService(
    private val clientUserRepository: ClientUserRepository,
) : IPaymentService {

    override suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment? {
        return clientUserRepository.getClientUserByUuid(clientUuid.toUuid())?.company?.payment
    }

}