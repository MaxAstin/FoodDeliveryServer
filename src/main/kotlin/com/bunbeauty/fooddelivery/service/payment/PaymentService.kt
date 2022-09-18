package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetPayment
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository

class PaymentService(
    private val clientUserRepository: IClientUserRepository,
) : IPaymentService {

    override suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment? {
        return clientUserRepository.getClientUserByUuid(clientUuid.toUuid())?.company?.payment
    }

}