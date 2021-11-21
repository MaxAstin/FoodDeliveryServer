package com.bunbeauty.food_delivery.data.repo.city

import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.model.city.GetCity

interface ICityRepository {

    suspend fun getCityList(): List<GetCity>
}