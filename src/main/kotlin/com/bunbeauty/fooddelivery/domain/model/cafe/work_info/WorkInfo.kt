package com.bunbeauty.fooddelivery.domain.model.cafe.work_info

import kotlinx.serialization.Serializable

/**
 * Class describes company work flow
 * @workType - work state for company
 * @workLoad - workload for cafe
 */
@Serializable
data class WorkInfo(
    val workType: WorkType,
    val workload: Workload
)
