package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.toUuid

class IsOrderAvailableV2UseCase(
    private val companyRepository: CompanyRepository,
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(companyUuid: String, cafeUuid: String): Boolean {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)
        val cafe = cafeRepository.getCafeByUuid(uuid = cafeUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)
        val isCompanyAvailable = getIsCompanyAvailable(company = company)
        val isCafeAvailable = getIsCafeAvailable(cafe = cafe)
        return isCompanyAvailable && isCafeAvailable
    }

    private fun getIsCompanyAvailable(company: Company): Boolean {
        return company.isOpen && company.workType != WorkType.CLOSED
    }

    private fun getIsCafeAvailable(cafe: Cafe): Boolean {
        return cafe.workType != WorkType.CLOSED
    }
}
