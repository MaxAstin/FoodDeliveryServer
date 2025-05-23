package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.toUuid

@Deprecated(
    "Used for mobile 2.3.1 for /order_availability" +
        "after minimum 2.4.0 for mobile can be deleted"
)
class IsOrderAvailableUseCase(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(companyUuid: String): Boolean {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)
        val isCompanyAvailable = getIsCompanyAvailable(company = company)
        return isCompanyAvailable
    }

    private fun getIsCompanyAvailable(company: Company): Boolean {
        return company.isOpen && company.workType != WorkType.CLOSED
    }
}
