package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionGroup
import java.util.*

val mapPostAdditionGroup: PostAdditionGroup.(UUID) -> InsertAdditionGroup = { companyUuid ->
    InsertAdditionGroup(
        name = name,
        singleChoice = singleChoice,
        priority = priority,
        isVisible = isVisible,
        companyUuid = companyUuid
    )
}
