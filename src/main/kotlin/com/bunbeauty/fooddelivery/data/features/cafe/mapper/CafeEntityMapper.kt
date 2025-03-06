package com.bunbeauty.fooddelivery.data.features.cafe.mapper

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones

val mapCafeWithZonesEntity: CafeEntity.() -> CafeWithZones = {
    CafeWithZones(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phoneNumber,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = city.uuid,
        isVisible = isVisible,
        workType = workType,
        workload = workload,
        zones = zones.map(mapDeliveryZoneEntity)
    )
}

val mapCafeEntityToCafeWithCity: CafeEntity.() -> CafeWithCity = {
    CafeWithCity(
        cafeWithZones = mapCafeWithZonesEntity(),
        city = city.mapCityEntity()
    )
}
