package com.bunbeauty.food_delivery.data.model.user

import com.bunbeauty.food_delivery.data.enums.UserRole
import java.util.*

data class InsertUser(
    val username: String,
    val passwordHash: String,
    val role: UserRole,
    val companyUuid: UUID,
)
