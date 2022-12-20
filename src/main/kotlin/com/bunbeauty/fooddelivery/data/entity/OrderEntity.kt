package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.model.order.*
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
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
        timeZone = cafe.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription ?: getAddress(),
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = calculateOldTotalCost(),
        newTotalCost = calculateNewTotalCost(),
        clientUserUuid = clientUser.uuid,
        oderProductList = oderProducts.map { oderProductEntity ->
            oderProductEntity.toOrderProduct()
        }
    )

    fun toClientOrderV2() = GetClientOrderV2(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafe.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        address = GetOrderAddress(
            description = addressDescription,
            street = addressStreet,
            house = addressHouse,
            flat = addressFlat,
            entrance = addressEntrance,
            floor = addressFloor,
            comment = addressComment,
        ),
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = calculateOldTotalCost(),
        newTotalCost = calculateNewTotalCost(),
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
        timeZone = cafe.city.timeZone,
        deferredTime = deferredTime,
        cafeUuid = cafe.uuid,
    )

    fun toCafeOrderDetails() = GetCafeOrderDetails(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafe.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription ?: getAddress(),
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = calculateOldTotalCost(),
        newTotalCost = calculateNewTotalCost(),
        clientUser = clientUser.toCafeUser(),
        cafeUuid = cafe.uuid,
        oderProductList = oderProducts.map { oderProductEntity ->
            oderProductEntity.toOrderProduct()
        },
        availableStatusList = getAvailableStatusList(),
    )

    fun toCafeOrderDetailsV2() = GetCafeOrderDetailsV2(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafe.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        address = GetOrderAddress(
            description = addressDescription,
            street = addressStreet,
            house = addressHouse,
            flat = addressFlat,
            entrance = addressEntrance,
            floor = addressFloor,
            comment = addressComment,
        ),
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = calculateOldTotalCost(),
        newTotalCost = calculateNewTotalCost(),
        clientUser = clientUser.toCafeUser(),
        cafeUuid = cafe.uuid,
        oderProductList = oderProducts.map { oderProductEntity ->
            oderProductEntity.toOrderProduct()
        },
        availableStatusList = getAvailableStatusList(),
    )

    private fun getAvailableStatusList(): List<String> = buildList {
        add(OrderStatus.NOT_ACCEPTED.name)
        if (deferredTime != null) {
            add(OrderStatus.ACCEPTED.name)
        }
        add(OrderStatus.PREPARING.name)
        if (isDelivery) {
            add(OrderStatus.SENT_OUT.name)
        } else {
            add(OrderStatus.DONE.name)
        }
        add(OrderStatus.DELIVERED.name)
        add(OrderStatus.CANCELED.name)
    }

    private fun getAddress(): String {
        return addressStreet +
                getAddressPart(data = addressHouse, prefix = ", д. ") +
                getAddressPart(data = addressFlat, prefix = ", кв. ") +
                getAddressPart(data = addressEntrance, prefix = ", ", postfix = " подъезд") +
                getAddressPart(data = addressFloor, prefix = ", ", postfix = " этаж") +
                getAddressPart(data = addressComment, prefix = ", ")
    }

    private fun getAddressPart(data: String?, prefix: String = "", postfix: String = ""): String {
        return if (data.isNullOrEmpty()) {
            ""
        } else {
            "$prefix$data$postfix"
        }
    }

    private fun calculateNewTotalCost(): Int {
        return oderProducts.sumOf { orderProductEntity ->
            orderProductEntity.count * orderProductEntity.newPrice
        } + (deliveryCost ?: 0)
    }

    private fun calculateOldTotalCost(): Int? {
        val isOldTotalCostEnabled = oderProducts.any { orderProductEntity ->
            orderProductEntity.oldPrice != null
        }
        return if (isOldTotalCostEnabled) {
            oderProducts.sumOf { orderProductEntity ->
                orderProductEntity.count * (orderProductEntity.oldPrice ?: orderProductEntity.newPrice)
            } + (deliveryCost ?: 0)
        } else {
            null
        }
    }

}