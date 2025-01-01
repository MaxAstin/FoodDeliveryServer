package com.bunbeauty.fooddelivery.domain.model.company.work_info

import kotlinx.serialization.Serializable

/**
 * Class describes company work flow
 * @isOpen - can or not user make order
 * @workType - work state for company
 */
@Serializable
class WorkInfo(
    val isOpen: Boolean,
    val workType: WorkType
)