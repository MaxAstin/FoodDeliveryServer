package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.domain.feature.address.model.Street

val mapStreetEntity: StreetEntity.() -> Street = {
    Street(
        uuid = uuid,
        name = name,
        latitude = latitude,
        longitude = longitude,
        cityUuid = cafe.city.uuid,
        cafeUuid = cafe.uuid,
        companyUuid = cafe.city.company.uuid,
        isVisible = isVisible,
    )
}