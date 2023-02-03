package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.order.cafe.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.table.OrderProductTable
import com.bunbeauty.fooddelivery.data.table.OrderTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class StatisticOrderEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var status: String by OrderTable.status
    var time: Long by OrderTable.time
    val orderProducts: SizedIterable<StatisticOrderProductEntity> by StatisticOrderProductEntity referrersOn OrderProductTable.order

    companion object : UUIDEntityClass<StatisticOrderEntity>(OrderTable)

    fun toStatisticOrder() = GetStatisticOrder(
        uuid = uuid,
        status = status,
        time = time,
        oderProductList = orderProducts.map { orderProductEntity ->
            orderProductEntity.toStatisticOrderProduct()
        }
    )

}