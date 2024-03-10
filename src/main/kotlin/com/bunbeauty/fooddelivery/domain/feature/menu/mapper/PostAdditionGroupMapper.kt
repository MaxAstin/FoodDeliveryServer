package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionGroup
import com.bunbeauty.fooddelivery.domain.mapUuid
import java.util.*

val mapPostAdditionGroup: PostAdditionGroup.(UUID) -> InsertAdditionGroup = { companyUuid ->
    InsertAdditionGroup(
        groupName = groupName,
        groupPriority = groupPriority,
        singleChoice = singleChoice,
        menuProductUuids = menuProductUuids.map(mapUuid),
        additionUuids = additionUuids.map(mapUuid),
        companyUuid = companyUuid,
    )
}