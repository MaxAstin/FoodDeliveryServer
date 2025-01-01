package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import java.util.*

class InsertAdditionToGroup(
    val additionGroupUuid: UUID,
    val additionUuid: UUID,
    val isSelected: Boolean,
    val isVisible: Boolean
)
