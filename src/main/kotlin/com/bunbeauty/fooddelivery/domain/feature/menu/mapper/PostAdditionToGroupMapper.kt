package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionToGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionToGroup
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostAdditionToGroup: PostAdditionToGroup.() -> InsertAdditionToGroup = {
    InsertAdditionToGroup(
        additionGroupUuid = groupUuid.toUuid(),
        additionUuid = additionUuid.toUuid(),
        isSelected = isSelected,
        isVisible = isVisible,
    )
}