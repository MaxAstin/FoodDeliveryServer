package com.bunbeauty.fooddelivery.service.delivery

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository

class DeliveryService(private val companyRepository: CompanyRepository) : IDeliveryService {

    override suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery? =
        companyRepository.getCompanyByUuid(companyUuid.toUuid())?.delivery

}