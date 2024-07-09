package com.bunbeauty.fooddelivery.service.delivery

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery
import com.bunbeauty.fooddelivery.domain.toUuid

class DeliveryService(private val companyRepository: CompanyRepository) : IDeliveryService {

    override suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery? =
        companyRepository.getCompanyByUuid(companyUuid.toUuid())?.delivery

}