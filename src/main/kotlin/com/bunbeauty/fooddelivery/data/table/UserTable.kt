package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.id.UUIDTable

/**
 * Table for admin app
 * */
object UserTable : UUIDTable() {

    val username = varchar("username", 512).uniqueIndex()
    val passwordHash = varchar("passwordHash", 512)
    val notificationToken = varchar("notificationToken", 512).nullable()
    val notificationDevice = varchar("notificationDevice", 512).nullable()
    val updateNotificationTokenDateTime = varchar("updateNotificationTokenDateTime", 512).nullable()
    val unlimitedNotification = bool("unlimitedNotification").default(true)
    val role = enumeration("role", UserRole::class)

    @Deprecated("Use cafe to connect tables, remove after 01.04.25")
    val city = reference("city", CityTable)

    val cafe = reference("cafe", CafeTable)
}
