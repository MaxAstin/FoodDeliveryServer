package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.model.client_user.GetClientUser
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.table.ClientUserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientUserEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserTable.phoneNumber
    var email: String? by ClientUserTable.email
    var company: CompanyEntity by CompanyEntity referencedOn ClientUserTable.company

    companion object : UUIDEntityClass<ClientUserEntity>(ClientUserTable)

    fun toClientUser(): GetClientUser {
        return GetClientUser(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email,
            company = company.toCompany()
        )
    }
}