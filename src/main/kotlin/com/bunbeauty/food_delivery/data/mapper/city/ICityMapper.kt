package com.bunbeauty.food_delivery.data.mapper.city

import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.model.city.GetCity

interface ICityMapper {

    fun entityToModel(cityEntity: CityEntity): GetCity
}