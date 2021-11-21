package com.bunbeauty.food_delivery.data.mapper.cafe

import com.bunbeauty.food_delivery.data.entity.CafeEntity
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe

class CafeMapper : ICafeMapper {

    override fun entityToModel(cafeEntity: CafeEntity): GetCafe {
        return GetCafe(
            uuid = cafeEntity.uuid.value,
            fromTime = cafeEntity.fromTime,
            toTime = cafeEntity.toTime,
            offset = cafeEntity.city.offset,
            phone = cafeEntity.phone,
            latitude = cafeEntity.latitude,
            longitude = cafeEntity.longitude,
            address = cafeEntity.address,
            cityUuid = cafeEntity.city.uuid.value,
            isVisible = cafeEntity.isVisible,
        )
    }
}