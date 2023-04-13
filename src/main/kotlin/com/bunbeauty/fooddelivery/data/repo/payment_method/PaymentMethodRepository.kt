package com.bunbeauty.fooddelivery.data.repo.payment_method

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.PaymentMethodEntity
import com.bunbeauty.fooddelivery.data.model.company.payment_method.GetPaymentMethod
import com.bunbeauty.fooddelivery.data.table.PaymentMethodTable
import java.util.*

class PaymentMethodRepository: IPaymentMethodRepository {

    override suspend fun getPaymentMethodListByCompanyUuid(companyUuid: UUID): List<GetPaymentMethod> = query {
        PaymentMethodEntity.find {
            PaymentMethodTable.company eq companyUuid
        }.map { paymentMethodEntity ->
            paymentMethodEntity.toPaymentMethod()
        }
    }

}