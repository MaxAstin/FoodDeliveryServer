package com.bunbeauty.fooddelivery.service.version

import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion

interface IVersionService {

    suspend fun getForceUpdateVersionByCompanyUuid(companyUuid: String): GetForceUpdateVersion?
}