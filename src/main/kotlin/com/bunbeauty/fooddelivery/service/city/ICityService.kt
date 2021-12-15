package com.bunbeauty.fooddelivery.service.city

import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.PostCity

interface ICityService {

    suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity
    suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity>
}