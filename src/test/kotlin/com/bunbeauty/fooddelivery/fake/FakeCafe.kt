package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload

object FakeCafe {
    fun create(
        uuid: String = "",
        fromTime: Int = 0,
        toTime: Int = 0,
        offset: Int = 3,
        phone: String = "",
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        address: String = "",
        cityUuid: String = "",
        isVisible: Boolean = true,
        workType: WorkType = WorkType.CLOSED,
        workload: Workload = Workload.LOW,
        zones: List<DeliveryZone> = emptyList()
    ) = Cafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid,
        isVisible = isVisible,
        workload = workload,
        workType = workType,
        zones = zones
    )
}
