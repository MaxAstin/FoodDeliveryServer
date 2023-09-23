package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientAuthSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientAuthSessionUuid
import com.bunbeauty.fooddelivery.data.table.ClientAuthSessionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientAuthSessionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientAuthSessionTable.phoneNumber
    var time: Long by ClientAuthSessionTable.time
    var attemptsLeft: Int by ClientAuthSessionTable.attemptsLeft
    var isConfirmed: Boolean by ClientAuthSessionTable.isConfirmed
    var company: CompanyEntity by CompanyEntity referencedOn ClientAuthSessionTable.company

    companion object : UUIDEntityClass<ClientAuthSessionEntity>(ClientAuthSessionTable)

    fun toClientAuthSessionUuid() = GetClientAuthSessionUuid(
        uuid = uuid
    )

    fun toClientAuthSession() = GetClientAuthSession(
        uuid = uuid,
        phoneNumber = phoneNumber,
        time = time,
        attemptsLeft = attemptsLeft,
        isConfirmed = isConfirmed,
        companyUuid = company.uuid,
    )
}