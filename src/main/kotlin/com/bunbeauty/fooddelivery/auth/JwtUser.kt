package com.bunbeauty.fooddelivery.auth

import com.bunbeauty.fooddelivery.data.enums.UserRole
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

    fun isClient() : Boolean {
        return role == UserRole.CLIENT.roleName
    }
}
