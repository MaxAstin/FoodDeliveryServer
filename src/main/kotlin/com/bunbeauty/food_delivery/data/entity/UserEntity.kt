package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.table.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var username: String by UserTable.username
    var passwordHash: String by UserTable.passwordHash
    var role: UserRole by UserTable.role
    var company: CompanyEntity by CompanyEntity referencedOn UserTable.company

    companion object : UUIDEntityClass<UserEntity>(UserTable)

    fun toUser(): GetUser {
        return GetUser(
            uuid = uuid,
            username = username,
            passwordHash = passwordHash,
            company = company.toCompany(),
            role = role.roleName,
        )
    }
}