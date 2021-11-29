package com.bunbeauty.food_delivery.data.repo.city

import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.InsertCity
import java.util.*

interface ICityRepository {

    suspend fun insertCity(insertCity: InsertCity): GetCity
    suspend fun getCityListByCompanyUuid(companyUuid: UUID): List<GetCity>
}