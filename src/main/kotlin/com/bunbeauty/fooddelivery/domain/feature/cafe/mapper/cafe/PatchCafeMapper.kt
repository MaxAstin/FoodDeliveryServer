package com.bunbeauty.fooddelivery.domain.feature.cafe.mapper.cafe

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PatchCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.UpdateCafe
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload

val mapPatchCafe: PatchCafe.() -> UpdateCafe = {
    UpdateCafe(
        fromTime = fromTime,
        toTime = toTime,
        offset = offset,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        isVisible = isVisible,
        workType = workType?.let { workType ->
            WorkType.valueOf(workType)
        },
        workload = workload?.let { workload ->
            Workload.valueOf(workload)
        }
    )
}
