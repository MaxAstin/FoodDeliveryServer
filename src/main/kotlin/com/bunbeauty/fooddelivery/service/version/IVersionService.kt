package com.bunbeauty.fooddelivery.service.version

import com.bunbeauty.fooddelivery.data.model.company.GetForceUpdateVersion

interface IVersionService {

    suspend fun getForceUpdateVersionByCompanyUuid(companyUuid: String): GetForceUpdateVersion?
}