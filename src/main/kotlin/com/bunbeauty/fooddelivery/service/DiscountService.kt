package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetDiscount
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository

class DiscountService(private val companyRepository: CompanyRepository) {

    suspend fun getDiscountByCompanyUuid(companyUuid: String): GetDiscount? {
        return companyRepository.getCompanyByUuid(companyUuid.toUuid())?.let { company ->
            GetDiscount(firstOrderDiscountPercent = company.percentDiscount)
        }
    }
}