package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import java.util.*

class InsertAdditionGroup(
    val groupName: String,
    val groupPriority: Int,
    val singleChoice: Boolean,
    val menuProductUuids: List<UUID>,
    val additionUuids: List<UUID>,
    val companyUuid: UUID
)