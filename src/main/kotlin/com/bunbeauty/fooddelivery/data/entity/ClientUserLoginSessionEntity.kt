package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetClientUserLoginSession
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.data.table.ClientUserLoginSessionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientUserLoginSessionEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserLoginSessionTable.phoneNumber
    var time: Long by ClientUserLoginSessionTable.time
    var code: String by ClientUserLoginSessionTable.code

    companion object : UUIDEntityClass<ClientUserLoginSessionEntity>(ClientUserLoginSessionTable)

    fun toClientUserLoginSession() = GetClientUserLoginSessionUuid(
        uuid = uuid
    )

    fun toClientUserLoginSessionWithCode() = GetClientUserLoginSession(
        uuid = uuid,
        phoneNumber = phoneNumber,
        time = time,
        code = code
    )
}