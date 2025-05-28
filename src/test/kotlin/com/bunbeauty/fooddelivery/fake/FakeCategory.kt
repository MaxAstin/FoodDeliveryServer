package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.Category

object FakeCategory {
    fun create(
        uuid: String = "",
        name: String = "",
        priority: Int = 0,
    ) = Category(
        uuid = uuid,
        name = name,
        priority = priority,
    )
}
