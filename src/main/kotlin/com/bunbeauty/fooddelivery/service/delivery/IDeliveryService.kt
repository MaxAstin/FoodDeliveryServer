package com.bunbeauty.fooddelivery.service.delivery

import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery

interface IDeliveryService {

    suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery?
}