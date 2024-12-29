package com.bunbeauty.fooddelivery.service.payment_method

import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPaymentMethod

interface IPaymentMethodService {

    suspend fun getPaymentMethodByCompanyUuid(companyUuid: String): List<GetPaymentMethod>
}
