package com.bunbeauty.fooddelivery.data.repo.city

import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.InsertCity
import java.util.*

interface ICityRepository {

    suspend fun insertCity(insertCity: InsertCity): GetCity
    suspend fun getCityListByCompanyUuid(companyUuid: UUID): List<GetCity>
}