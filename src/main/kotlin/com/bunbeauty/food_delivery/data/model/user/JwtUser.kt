package com.bunbeauty.food_delivery.data.model.user

import com.bunbeauty.food_delivery.data.enums.UserRole
import io.ktor.auth.*

data class JwtUser(
    val uuid: String,
    val role: String,
) : Principal {

    fun isAdmin() : Boolean {
        return role == UserRole.ADMIN.roleName
    }

    fun isManager() : Boolean {
        return role == UserRole.MANAGER.roleName
    }
}
