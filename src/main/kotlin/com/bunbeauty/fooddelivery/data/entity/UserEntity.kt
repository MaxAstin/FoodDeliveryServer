package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.table.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var username: String by UserTable.username
    var passwordHash: String by UserTable.passwordHash
    var notificationToken: String? by UserTable.notificationToken
    var notificationDevice: String? by UserTable.notificationDevice
    var updateNotificationTokenDateTime: String? by UserTable.updateNotificationTokenDateTime
    var unlimitedNotification: Boolean by UserTable.unlimitedNotification
    var role: UserRole by UserTable.role

    var cafe: CafeEntity by CafeEntity referencedOn UserTable.cafe

    companion object : UUIDEntityClass<UserEntity>(UserTable)
}
