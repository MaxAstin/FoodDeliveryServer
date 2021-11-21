package com.bunbeauty.food_delivery.service.city

import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.repo.city.ICityRepository

class CityService(private val cityRepository: ICityRepository) : ICityService {

    override suspend fun getCityList(): List<GetCity> = cityRepository.getCityList()
}