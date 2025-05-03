package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object ClientUserTable : UUIDTable() {

    val phoneNumber = varchar("phoneNumber", 512)
    val email = varchar("email", 512).nullable()
    val isActive = bool("isActive").default(true)
    val company = reference("company", CompanyTable)
    val notificationToken = varchar("notificationToken", 512).nullable()
    val notificationDevice = varchar("notificationDevice", 512).nullable()
    val updateNotificationTokenDateTime = varchar("updateNotificationTokenDateTime", 512).nullable()
}
