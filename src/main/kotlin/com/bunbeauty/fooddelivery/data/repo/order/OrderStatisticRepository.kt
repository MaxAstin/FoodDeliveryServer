package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.order.OrderEntity
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
import com.bunbeauty.fooddelivery.domain.model.statistic.GetStatisticOrder
import org.jetbrains.exposed.sql.and
import java.util.*

class OrderStatisticRepository : IOrderStatisticRepository {

    override suspend fun getOrderListByCafeUuid(cafeUuid: UUID, fromTime: Long, toTime: Long): List<GetStatisticOrder> =
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
                                OrderStatus.DONE.name,
                            )
                        )
            }.map { orderEntity ->
                orderEntity.toStatisticOrder()
            }
        }

    override suspend fun getOrderListByCompanyUuid(
        companyUuid: UUID,
        fromTime: Long,
        toTime: Long,
    ): List<GetStatisticOrder> = query {
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
                            OrderStatus.DONE.name,
                        )
                    )
        }.map { orderEntity ->
            orderEntity.toStatisticOrder()
        }
    }

}