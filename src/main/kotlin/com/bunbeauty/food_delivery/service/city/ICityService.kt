package com.bunbeauty.food_delivery.service.city

import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.PostCity

interface ICityService {

    suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity
    suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity>
}