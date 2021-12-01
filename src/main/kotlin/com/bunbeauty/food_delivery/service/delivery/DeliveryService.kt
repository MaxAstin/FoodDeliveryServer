package com.bunbeauty.food_delivery.service.delivery

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.delivery.GetDelivery
import com.bunbeauty.food_delivery.data.repo.company.ICompanyRepository

class DeliveryService(private val companyRepository: ICompanyRepository) : IDeliveryService {

    override suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery? =
        companyRepository.getCompanyByUuid(companyUuid.toUuid())?.delivery

}