package com.bunbeauty.fooddelivery.domain.model.non_working_day

import kotlinx.serialization.Serializable

@Serializable
class PostNonWorkingDay(
    val timestamp: Long,
    val cafeUuid: String,
)