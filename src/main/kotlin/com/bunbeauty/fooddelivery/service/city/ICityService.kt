package com.bunbeauty.fooddelivery.service.city

import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import com.bunbeauty.fooddelivery.domain.model.city.PostCity

interface ICityService {

    suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity
    suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity>
}