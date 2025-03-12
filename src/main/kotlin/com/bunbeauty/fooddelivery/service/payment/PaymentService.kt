package com.bunbeauty.fooddelivery.service.payment

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapPayment
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment

class PaymentService(
    private val companyRepository: CompanyRepository
) : IPaymentService {

    override suspend fun getPaymentByClientUuid(clientUuid: String): GetPayment {
        val clientUser = companyRepository.getCompanyByUserUuid(userUuid = clientUuid)
            .orThrowNotFoundByUuidError(clientUuid)

        return clientUser.payment.mapPayment()
    }
}
