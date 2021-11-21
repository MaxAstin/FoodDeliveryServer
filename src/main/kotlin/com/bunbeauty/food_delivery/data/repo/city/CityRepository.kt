package com.bunbeauty.food_delivery.data.repo.city

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.model.city.GetCity

class CityRepository : ICityRepository {

    override suspend fun getCityList(): List<GetCity> {
        return query {
            CityEntity.all().map { cityEntity ->
                cityEntity.toCity()
            }.toList()
        }
    }
}