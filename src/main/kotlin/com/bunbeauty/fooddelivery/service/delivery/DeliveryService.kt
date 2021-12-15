package com.bunbeauty.fooddelivery.service.delivery

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository

class DeliveryService(private val companyRepository: ICompanyRepository) : IDeliveryService {

    override suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery? =
        companyRepository.getCompanyByUuid(companyUuid.toUuid())?.delivery

}