package com.bunbeauty.fooddelivery.data.init

import com.bunbeauty.fooddelivery.data.enums.UserRole

class InitUser(
    val username: String,
    val passwordHash: String,
    val role: UserRole
)
