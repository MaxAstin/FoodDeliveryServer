package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.CategoryEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.Category

val mapCategoryEntity: CategoryEntity.() -> Category = {
    Category(
        uuid = uuid,
        name = name,
        priority = priority,
    )
}