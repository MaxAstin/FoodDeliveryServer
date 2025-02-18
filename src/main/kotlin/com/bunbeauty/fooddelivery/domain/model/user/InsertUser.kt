package com.bunbeauty.fooddelivery.domain.model.user

import com.bunbeauty.fooddelivery.data.enums.UserRole
import java.util.*

class InsertUser(
    val username: String,
    val passwordHash: String,
    val role: UserRole,
    val cafeUuid: UUID
)
