package com.bunbeauty.fooddelivery.service.payment_method

import com.bunbeauty.fooddelivery.data.repo.payment_method.IPaymentMethodRepository
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPaymentMethod
import com.bunbeauty.fooddelivery.domain.toUuid

class PaymentMethodService(
    private val paymentMethodRepository: IPaymentMethodRepository
): IPaymentMethodService {

    override suspend fun getPaymentMethodByCompanyUuid(companyUuid: String): List<GetPaymentMethod> {
        return paymentMethodRepository.getPaymentMethodListByCompanyUuid(companyUuid.toUuid())
    }

}