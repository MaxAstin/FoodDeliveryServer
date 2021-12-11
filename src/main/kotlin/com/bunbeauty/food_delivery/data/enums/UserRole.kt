package com.bunbeauty.food_delivery.data.enums

enum class UserRole(val roleName: String) {
    ADMIN("admin"),
    MANAGER("manager"),
    CLIENT("client"),
    UNKNOWN("unknown");

    companion object {
        fun findByRoleName(roleName: String): UserRole {
            return values().find { userRole ->
                userRole.roleName == roleName
            } ?: UNKNOWN
        }
    }
}