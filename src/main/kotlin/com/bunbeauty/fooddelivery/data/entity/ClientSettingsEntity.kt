package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientSettingsEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserTable.phoneNumber
    var email: String? by ClientUserTable.email

    companion object : UUIDEntityClass<ClientSettingsEntity>(ClientUserTable)

    fun toClientSetting(): GetClientSettings {
        return GetClientSettings(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email,
        )
    }

}