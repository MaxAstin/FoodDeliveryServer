package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import java.util.*

class InsertAdditionGroup(
    val name: String,
    val singleChoice: Boolean,
    val priority: Int,
    val isVisible: Boolean,
    val companyUuid: UUID
)
