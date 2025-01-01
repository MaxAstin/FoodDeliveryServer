package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import java.util.*

data class InsertAdditionGroupToMenuProducts(
    val additionGroupUuid: UUID,
    val additionUuids: List<UUID>,
    val menuProductUuids: List<UUID>,
)
