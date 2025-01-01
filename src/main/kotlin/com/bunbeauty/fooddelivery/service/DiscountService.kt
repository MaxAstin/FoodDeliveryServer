package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.company.discount.GetDiscount
import com.bunbeauty.fooddelivery.domain.toUuid

class DiscountService(private val companyRepository: CompanyRepository) {

    suspend fun getDiscountByCompanyUuid(companyUuid: String): GetDiscount {
        val company = companyRepository.getCompanyByUuid(
            uuid = companyUuid.toUuid()
        ).orThrowNotFoundByUuidError(uuid = companyUuid)

        return GetDiscount(firstOrderDiscountPercent = company.percentDiscount)
    }
}
