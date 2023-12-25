package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment

class PaymentService(
    private val clientUserRepository: ClientUserRepository,
) : IPaymentService {

    override suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment? {
        return clientUserRepository.getClientUserByUuid(uuid = clientUuid)?.company?.payment
    }

}