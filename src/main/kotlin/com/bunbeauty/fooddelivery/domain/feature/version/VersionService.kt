package com.bunbeauty.fooddelivery.domain.feature.version

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion
import com.bunbeauty.fooddelivery.domain.toUuid

class VersionService(private val companyRepository: CompanyRepository) {

    suspend fun getForceUpdateVersionByCompanyUuid(companyUuid: String): GetForceUpdateVersion {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)

        return GetForceUpdateVersion(version = company.forceUpdateVersion)
    }
}
