package com.bunbeauty.fooddelivery.data.features.menu.mapper

import com.bunbeauty.fooddelivery.data.entity.menu.CategoryEntity
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.Category

val mapCategoryEntity: CategoryEntity.() -> Category = {
    Category(
        uuid = uuid,
        name = name,
        priority = priority,
    )
}