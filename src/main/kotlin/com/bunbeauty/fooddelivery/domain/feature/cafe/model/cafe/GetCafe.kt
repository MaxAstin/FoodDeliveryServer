package com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe

import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload
import kotlinx.serialization.Serializable

@Serializable
class GetCafe(
    val uuid: String,
    val fromTime: Int,
    val toTime: Int,
    val offset: Int,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val cityUuid: String,
    val isVisible: Boolean,
    val workType: WorkType,
    val workload: Workload
)
