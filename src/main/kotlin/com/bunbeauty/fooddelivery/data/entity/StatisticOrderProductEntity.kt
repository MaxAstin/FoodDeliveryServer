package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.order.OrderProductTable
import com.bunbeauty.fooddelivery.domain.model.order.GetStatisticOrderProduct
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StatisticOrderProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var count: Int by OrderProductTable.count
    var name: String by OrderProductTable.name
    var newPrice: Int by OrderProductTable.newPrice
    var oldPrice: Int? by OrderProductTable.oldPrice

    companion object : UUIDEntityClass<StatisticOrderProductEntity>(OrderProductTable)

    fun toStatisticOrderProduct() = GetStatisticOrderProduct(
        uuid = uuid,
        count = count,
        newPrice = newPrice
    )
}
