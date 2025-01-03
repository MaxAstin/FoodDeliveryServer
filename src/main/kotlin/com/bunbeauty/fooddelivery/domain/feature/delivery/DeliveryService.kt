package com.bunbeauty.fooddelivery.domain.feature.delivery

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapDelivery
import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery
import com.bunbeauty.fooddelivery.domain.toUuid

class DeliveryService(private val companyRepository: CompanyRepository) {

    suspend fun getDeliveryByCompanyUuid(companyUuid: String): GetDelivery {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)

        return company.delivery.mapDelivery()
    }
}
