package com.bunbeauty.fooddelivery.data.model.category

import java.util.*

data class InsertCategory(
    val name: String,
    val priority: Int,
    val companyUuid: UUID
)
