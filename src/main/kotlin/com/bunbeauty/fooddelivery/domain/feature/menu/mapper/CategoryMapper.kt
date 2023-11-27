package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.Category
import com.bunbeauty.fooddelivery.domain.model.category.GetCategory

val mapCategory: Category.() -> GetCategory = {
    GetCategory(
        uuid = uuid,
        name = name,
        priority = priority,
    )
}