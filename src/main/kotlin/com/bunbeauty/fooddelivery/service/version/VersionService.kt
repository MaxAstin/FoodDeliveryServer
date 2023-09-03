package com.bunbeauty.fooddelivery.service.version

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetForceUpdateVersion
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository

class VersionService(private val companyRepository: CompanyRepository) : IVersionService {

    override suspend fun getForceUpdateVersionByCompanyUuid(companyUuid: String): GetForceUpdateVersion? {
        return companyRepository.getCompanyByUuid(companyUuid.toUuid())?.forceUpdateVersion
    }
}