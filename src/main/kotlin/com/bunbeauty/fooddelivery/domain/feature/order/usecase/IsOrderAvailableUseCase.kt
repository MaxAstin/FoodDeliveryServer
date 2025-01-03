package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.toUuid

class IsOrderAvailableUseCase(
    private val companyRepository: CompanyRepository
) {

    suspend operator fun invoke(companyUuid: String): Boolean {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)

        return company.isOpen
    }
}
