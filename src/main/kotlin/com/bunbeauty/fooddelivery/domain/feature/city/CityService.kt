package com.bunbeauty.fooddelivery.domain.feature.city

import com.bunbeauty.fooddelivery.data.features.city.CityRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.city.mapper.mapCity
import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import com.bunbeauty.fooddelivery.domain.model.city.InsertCity
import com.bunbeauty.fooddelivery.domain.model.city.PostCity
import com.bunbeauty.fooddelivery.domain.toUuid

class CityService(
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository,
) {

    suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity {
        val user = userRepository.getUserByUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUuidError(creatorUuid)
        val insertCity = InsertCity(
            name = postCity.name,
            timeZone = postCity.timeZone,
            company = user.companyUuid.toUuid(),
            isVisible = postCity.isVisible,
        )

        return cityRepository.insertCity(insertCity).mapCity()
    }

    suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity> {
        return cityRepository.getCityListByCompanyUuid(companyUuid.toUuid())
            .map(mapCity)
    }

}