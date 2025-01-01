package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionGroupToMenuProducts
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionGroupToMenuProducts
import com.bunbeauty.fooddelivery.domain.mapUuid
import com.bunbeauty.fooddelivery.domain.toUuid

val mapPostAdditionGroupToMenuProducts: PostAdditionGroupToMenuProducts.() -> InsertAdditionGroupToMenuProducts = {
    InsertAdditionGroupToMenuProducts(
        additionGroupUuid = additionGroupUuid.toUuid(),
        additionUuids = additionUuids.map(mapUuid),
        menuProductUuids = menuProductUuids.map(mapUuid),
    )
}