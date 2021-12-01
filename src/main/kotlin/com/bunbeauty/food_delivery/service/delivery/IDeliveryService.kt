package com.bunbeauty.food_delivery.service.delivery

import com.bunbeauty.food_delivery.data.model.delivery.GetDelivery

interface IDeliveryService {

    suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery?
}