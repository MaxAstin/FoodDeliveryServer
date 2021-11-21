package com.bunbeauty.food_delivery.data.mapper.city

import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.model.city.GetCity

class CityMapper : ICityMapper {

    override fun entityToModel(cityEntity: CityEntity): GetCity {
        return GetCity(
            uuid = cityEntity.uuid.value,
            name = cityEntity.name,
            offset = cityEntity.offset,
            isVisible = cityEntity.isVisible,
        )
    }
}