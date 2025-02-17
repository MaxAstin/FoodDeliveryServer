package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.enums.UserRole
import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable() {

    val username = varchar("username", 512).uniqueIndex()
    val passwordHash = varchar("passwordHash", 512)
    val notificationToken = varchar("notificationToken", 512).nullable()
    val notificationDevice = varchar("notificationDevice", 512).nullable()
    val updateNotificationTokenDateTime = varchar("updateNotificationTokenDateTime", 512).nullable()
    val unlimitedNotification = bool("unlimitedNotification").default(true)
    val role = enumeration("role", UserRole::class)
    val city = reference("city", CityTable)
}
