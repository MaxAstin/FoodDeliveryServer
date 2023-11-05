package com.bunbeauty.fooddelivery.service.city

import com.bunbeauty.fooddelivery.data.repo.city.ICityRepository
import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import com.bunbeauty.fooddelivery.domain.model.city.InsertCity
import com.bunbeauty.fooddelivery.domain.model.city.PostCity
import com.bunbeauty.fooddelivery.domain.toUuid

class CityService(private val cityRepository: ICityRepository) :
    ICityService {

    override suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity {
        val insertCity = InsertCity(
            name = postCity.name,
            timeZone = postCity.timeZone,
            company = postCity.companyUuid.toUuid(),
            isVisible = postCity.isVisible,
        )

        return cityRepository.insertCity(insertCity)
    }

    override suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity> =
        cityRepository.getCityListByCompanyUuid(companyUuid.toUuid())
}