package com.bunbeauty.fooddelivery.data.repo.payment_method

import com.bunbeauty.fooddelivery.data.model.company.payment_method.GetPaymentMethod
import java.util.UUID

interface IPaymentMethodRepository {

    suspend fun getPaymentMethodListByCompanyUuid(companyUuid: UUID): List<GetPaymentMethod>

}