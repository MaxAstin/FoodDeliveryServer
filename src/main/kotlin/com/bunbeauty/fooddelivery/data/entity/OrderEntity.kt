package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.table.OrderProductTable
import com.bunbeauty.fooddelivery.data.table.OrderTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class OrderEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var time: Long by OrderTable.time
    var isDelivery: Boolean by OrderTable.isDelivery
    var code: String by OrderTable.code
    var addressDescription: String by OrderTable.addressDescription
    var comment: String? by OrderTable.comment
    var deferredTime: Long? by OrderTable.deferredTime
    var status: String by OrderTable.status
    var deliveryCost: Int? by OrderTable.deliveryCost
    var cafe: CafeEntity by CafeEntity referencedOn OrderTable.cafe
    var company: CompanyEntity by CompanyEntity referencedOn OrderTable.company
    var clientUser: ClientUserEntity by ClientUserEntity referencedOn OrderTable.clientUser
    val oderProducts: SizedIterable<OrderProductEntity> by OrderProductEntity referrersOn OrderProductTable.order

    companion object : UUIDEntityClass<OrderEntity>(OrderTable)

    fun toClientOrder() = GetClientOrder(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription,
        comment = comment,
        deliveryCost = deliveryCost,
        clientUserUuid = clientUser.uuid,
        oderProductList = oderProducts.map { oderProductEntity ->
            oderProductEntity.toOrderProduct()
        }
    )

    fun toCafeOrder() = GetCafeOrder(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription,
        comment = comment,
        deliveryCost = deliveryCost,
        clientUser = clientUser.toCafeUser(),
        cafeUuid = cafe.uuid,
        oderProductList = oderProducts.map { oderProductEntity ->
            oderProductEntity.toOrderProduct()
        }
    )
}