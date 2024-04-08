package com.bunbeauty.fooddelivery.service.version

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion
import com.bunbeauty.fooddelivery.domain.toUuid

class VersionService(private val companyRepository: CompanyRepository) : IVersionService {

    override suspend fun getForceUpdateVersionByCompanyUuid(companyUuid: String): GetForceUpdateVersion? {
        return companyRepository.getCompanyByUuid(companyUuid.toUuid())?.forceUpdateVersion
    }
}