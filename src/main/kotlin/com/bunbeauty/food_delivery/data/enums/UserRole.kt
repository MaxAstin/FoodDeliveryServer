package com.bunbeauty.food_delivery.data.enums

enum class UserRole(val roleName: String) {
    ADMIN("admin"),
    MANAGER("manager");

    companion object {
        fun findByRoleName(roleName: String): UserRole {
            return values().find { userRole ->
                userRole.roleName == roleName
            } ?: MANAGER
        }
    }
}