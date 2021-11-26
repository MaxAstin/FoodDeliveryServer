package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.table.OrderTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OrderEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var isDelivery: Boolean by OrderTable.isDelivery
    var code: String by OrderTable.code
    var address: String by OrderTable.address
    var comment: String? by OrderTable.comment
    var deferredTime: Long? by OrderTable.deferredTime
    var status: String by OrderTable.status
    var addressUuid: String? by OrderTable.addressUuid
    var cafeUuid: String by OrderTable.cafeUuid
    var userUuid: String by OrderTable.userUuid

    companion object : UUIDEntityClass<OrderEntity>(OrderTable)

    fun toOrder() = GetOrder(
        uuid = uuid,
        isDelivery = isDelivery,
        code = code,
        address = address,
        comment = comment,
        deferredTime = deferredTime,
        status = status,
        addressUuid = addressUuid,
        cafeUuid = cafeUuid,
        userUuid = userUuid,
    )
}