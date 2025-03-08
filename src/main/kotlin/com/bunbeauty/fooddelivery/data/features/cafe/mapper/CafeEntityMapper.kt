package com.bunbeauty.fooddelivery.data.features.cafe.mapper

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntity
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones
import org.jetbrains.exposed.sql.ResultRow

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


fun ResultRow.mapCafeEntityToCafe() = Cafe(
    uuid = this[CafeTable.id].value.toString(),
    fromTime = this[CafeTable.fromTime],
    toTime = this[CafeTable.toTime],
    offset = this[CafeTable.offset],
    phone = this[CafeTable.phoneNumber],
    latitude = this[CafeTable.latitude],
    longitude = this[CafeTable.longitude],
    address = this[CafeTable.address],
    isVisible = this[CafeTable.isVisible],
    workType = this[CafeTable.workType],
    workload = this[CafeTable.workload],
    cityUuid = this[CafeTable.city].value.toString()
)
