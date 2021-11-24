package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.entity.conpany.CompanyEntity
import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.table.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(
    val entityId: EntityID<String>,
) : Entity<String>(entityId) {

    var uuid: String by UserTable.uuid
    var username: String by UserTable.username
    var passwordHash: String by UserTable.passwordHash
    var role: UserRole by UserTable.role
    var company: CompanyEntity by CompanyEntity referencedOn UserTable.company

    companion object : EntityClass<String, UserEntity>(UserTable)

    fun toModel(): GetUser {
        return GetUser(
            uuid = uuid,
            username = username,
            company = GetCompany(
                uuid = company.uuid.toString(),
                name = company.name
            ),
            role = role.roleName,
        )
    }
}