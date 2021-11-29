package com.bunbeauty.food_delivery.service.city

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.InsertCity
import com.bunbeauty.food_delivery.data.model.city.PostCity
import com.bunbeauty.food_delivery.data.repo.city.ICityRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository

class CityService(private val cityRepository: ICityRepository, private val userRepository: IUserRepository) :
    ICityService {

    override suspend fun createCity(creatorUuid: String, postCity: PostCity): GetCity {
        val insertCity = InsertCity(
            name = postCity.name,
            offset = postCity.offset,
            company = postCity.companyUuid.toUuid(),
            isVisible = postCity.isVisible,
        )

        return cityRepository.insertCity(insertCity)
    }

    override suspend fun getCityListByCompanyUuid(companyUuid: String): List<GetCity> =
        cityRepository.getCityListByCompanyUuid(companyUuid.toUuid())
}