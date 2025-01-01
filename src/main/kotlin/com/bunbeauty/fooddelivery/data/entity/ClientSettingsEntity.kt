package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientSettings
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ClientSettingsEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserTable.phoneNumber
    var email: String? by ClientUserTable.email
    var isActive: Boolean by ClientUserTable.isActive

    companion object : UUIDEntityClass<ClientSettingsEntity>(ClientUserTable)

    fun toClientSetting(): GetClientSettings {
        return GetClientSettings(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email,
            isActive = isActive,
        )
    }

}