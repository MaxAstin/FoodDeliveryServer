package com.bunbeauty.fooddelivery.data.model.non_working_day

import kotlinx.serialization.Serializable

@Serializable
class GetNonWorkingDay(
    val uuid: String,
    val timestamp: Long,
    val cafeUuid: String,
)