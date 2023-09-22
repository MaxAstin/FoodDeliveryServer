package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetAuthSession
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetAuthSessionUuid
import com.bunbeauty.fooddelivery.data.table.ClientUserLoginSessionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientUserLoginSessionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserLoginSessionTable.phoneNumber
    var time: Long by ClientUserLoginSessionTable.time
    var isConfirmed: Boolean by ClientUserLoginSessionTable.isConfirmed
    var company: CompanyEntity by CompanyEntity referencedOn ClientUserLoginSessionTable.company

    companion object : UUIDEntityClass<ClientUserLoginSessionEntity>(ClientUserLoginSessionTable)

    fun toAuthSessionUuid() = GetAuthSessionUuid(
        uuid = uuid
    )

    fun toAuthSession() = GetAuthSession(
        uuid = uuid,
        phoneNumber = phoneNumber,
        time = time,
        isConfirmed = isConfirmed,
        companyUuid = company.uuid,
    )
}