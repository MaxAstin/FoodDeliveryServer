package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.model.company.discount.GetDiscount
import com.bunbeauty.fooddelivery.domain.toUuid

class DiscountService(private val companyRepository: CompanyRepository) {

    suspend fun getDiscountByCompanyUuid(companyUuid: String): GetDiscount? {
        return companyRepository.getCompanyByUuid(companyUuid.toUuid())?.let { company ->
            GetDiscount(firstOrderDiscountPercent = company.percentDiscount)
        }
    }
}