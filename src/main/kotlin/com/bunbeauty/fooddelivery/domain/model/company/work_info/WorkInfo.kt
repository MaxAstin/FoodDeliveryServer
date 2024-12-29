package com.bunbeauty.fooddelivery.domain.model.company.work_info

import kotlinx.serialization.Serializable

/**
 * Class describes company work flow
 * @workType - work state for company
 */
@Serializable
class WorkInfo(
    val workType: WorkType
)
