package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.*
import com.bunbeauty.food_delivery.data.enums.OrderStatus
import com.bunbeauty.food_delivery.data.model.order.GetCafeOrder
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.InsertOrder
import com.bunbeauty.food_delivery.data.table.OrderTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.util.*

class OrderRepository : IOrderRepository {

    override suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder = query {
        val orderEntity = OrderEntity.new {
            time = insertOrder.time
            isDelivery = insertOrder.isDelivery
            code = insertOrder.code
            addressDescription = insertOrder.addressDescription
            comment = insertOrder.comment
            deferredTime = insertOrder.deferredTime
            status = insertOrder.status
            cafe = CafeEntity[insertOrder.cafeUuid]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            OrderProductEntity.new {
                menuProduct = MenuProductEntity[insertOrderProduct.menuProductUuid]
                count = insertOrderProduct.count
                order = orderEntity
            }
        }

        orderEntity.toClientOrder()
    }

    override suspend fun getOrderListByCafeUuid(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder> = query {
        OrderEntity.find {
            (OrderTable.cafe eq cafeUuid) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrder()
            }
    }

    override suspend fun updateOrderStatusByUuid(orderUuid: UUID, status: String): GetCafeOrder? = query {
        val orderEntity =  OrderEntity.findById(orderUuid)
        orderEntity?.status = status
        orderEntity?.toCafeOrder()
    }

    override suspend fun observeActiveOrderList(clientUserUuid: UUID): List<GetClientOrder> = query {
        OrderEntity.find {
            (OrderTable.clientUser eq clientUserUuid) and
                    (OrderTable.status neq OrderStatus.DELIVERED.name) and
                    (OrderTable.status neq OrderStatus.CANCELED.name)
        }.map { orderEntity ->
            orderEntity.toClientOrder()
        }
    }
}