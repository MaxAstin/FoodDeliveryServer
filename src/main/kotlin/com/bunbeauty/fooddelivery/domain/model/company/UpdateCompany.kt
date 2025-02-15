package com.bunbeauty.fooddelivery.domain.model.company

import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkType
import java.util.*

class UpdateCompany(
    val uuid: UUID,
    val name: String? = null,
    val forFreeDelivery: Int? = null,
    val deliveryCost: Int? = null,
    val forceUpdateVersion: Int? = null,
    val percentDiscount: Int? = null,
    val isOpen: Boolean? = null,
    val workType: WorkType? = null
)
