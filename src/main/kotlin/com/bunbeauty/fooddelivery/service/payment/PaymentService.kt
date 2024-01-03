package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapPayment
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment

class PaymentService(
    private val clientUserRepository: ClientUserRepository,
) : IPaymentService {

    override suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment {
        val clientUser = clientUserRepository.getClientUserByUuid(uuid = clientUuid)
            .orThrowNotFoundByUuidError(clientUuid)

        return clientUser.company.payment.mapPayment()
    }

}