package com.bunbeauty.fooddelivery.service.delivery

import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery

interface IDeliveryService {

    suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery?
}