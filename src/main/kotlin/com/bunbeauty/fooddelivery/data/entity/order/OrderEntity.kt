package com.bunbeauty.fooddelivery.data.entity.order

import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.order.OrderProductTable
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
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
    var addressDescription: String? by OrderTable.addressDescription
    var addressStreet: String? by OrderTable.addressStreet
    var addressHouse: String? by OrderTable.addressHouse
    var addressFlat: String? by OrderTable.addressFlat
    var addressEntrance: String? by OrderTable.addressEntrance
    var addressFloor: String? by OrderTable.addressFloor
    var addressComment: String? by OrderTable.addressComment
    var comment: String? by OrderTable.comment
    var deferredTime: Long? by OrderTable.deferredTime
    var status: String by OrderTable.status
    var deliveryCost: Int? by OrderTable.deliveryCost
    var paymentMethod: String? by OrderTable.paymentMethod
    var percentDiscount: Int? by OrderTable.percentDiscount
    var cafe: CafeEntity by CafeEntity referencedOn OrderTable.cafe
    var company: CompanyEntity by CompanyEntity referencedOn OrderTable.company
    var clientUser: ClientUserEntity by ClientUserEntity referencedOn OrderTable.clientUser

    val oderProducts: SizedIterable<OrderProductEntity> by OrderProductEntity referrersOn OrderProductTable.order

    companion object : UUIDEntityClass<OrderEntity>(OrderTable)
}
