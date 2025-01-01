package com.bunbeauty.fooddelivery.domain.feature.company.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.toUuid

class GetWorkInfoUseCase(
    private val companyRepository: CompanyRepository
) {

    suspend operator fun invoke(companyUuid: String): WorkInfo {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)

        return WorkInfo(
            workType = company.workType
        )
    }
}
