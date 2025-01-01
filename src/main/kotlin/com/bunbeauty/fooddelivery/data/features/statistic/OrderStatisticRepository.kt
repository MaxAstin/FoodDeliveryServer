package com.bunbeauty.fooddelivery.data.features.statistic

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.order.OrderEntity
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.order.mapper.mapOrderEntity
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import org.jetbrains.exposed.sql.and
import java.util.*

class OrderStatisticRepository {

    suspend fun getOrderListByCafeUuid(cafeUuid: UUID, fromTime: Long, toTime: Long): List<Order> =
        query {
            OrderEntity.find {
                (OrderTable.cafe eq cafeUuid) and
                    (OrderTable.time greater fromTime) and
                    (OrderTable.time less toTime) and
                    OrderTable.status.inList(
                        listOf(
                            OrderStatus.ACCEPTED.name,
                            OrderStatus.PREPARING.name,
                            OrderStatus.SENT_OUT.name,
                            OrderStatus.DELIVERED.name,
                            OrderStatus.DONE.name
                        )
                    )
            }.map(mapOrderEntity)
        }

    suspend fun getOrderListByCompanyUuid(
        companyUuid: UUID,
        fromTime: Long,
        toTime: Long
    ): List<Order> = query {
        OrderEntity.find {
            (OrderTable.company eq companyUuid) and
                (OrderTable.time greater fromTime) and
                (OrderTable.time less toTime) and
                OrderTable.status.inList(
                    listOf(
                        OrderStatus.ACCEPTED.name,
                        OrderStatus.PREPARING.name,
                        OrderStatus.SENT_OUT.name,
                        OrderStatus.DELIVERED.name,
                        OrderStatus.DONE.name
                    )
                )
        }.map(mapOrderEntity)
    }
}
