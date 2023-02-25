package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.OrderEntity
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.table.OrderTable
import org.jetbrains.exposed.sql.and
import java.util.*

class OrderStatisticRepository : IOrderStatisticRepository {

    override suspend fun getOrderListByCafeUuid(cafeUuid: UUID, fromTime: Long, toTime: Long): List<GetStatisticOrder> =
        query {
            OrderEntity.find {
                (OrderTable.cafe eq cafeUuid) and
                        (OrderTable.time greater fromTime) and
                        (OrderTable.time less toTime) and
                        (OrderTable.status eq "DELIVERED")
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
                    (OrderTable.status eq "DELIVERED")
        }.map { orderEntity ->
            orderEntity.toStatisticOrder()
        }
    }

}