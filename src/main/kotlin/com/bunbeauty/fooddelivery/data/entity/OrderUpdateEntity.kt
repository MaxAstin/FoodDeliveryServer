package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderUpdate
import com.bunbeauty.fooddelivery.data.table.OrderTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OrderUpdateEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    val status: String by OrderTable.status
    val clientUser: ClientUserEntity by ClientUserEntity referencedOn OrderTable.clientUser

    companion object : UUIDEntityClass<OrderUpdateEntity>(OrderTable)

    fun toClientOrderUpdate() = GetClientOrderUpdate(
        uuid = uuid,
        status = status,
        clientUserUuid = clientUser.uuid,
    )

}