package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.*
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.InsertOrder
import com.bunbeauty.fooddelivery.data.table.OrderTable
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
            deliveryCost = insertOrder.deliveryCost
            cafe = CafeEntity[insertOrder.cafeUuid]
            company = CompanyEntity[insertOrder.companyUuid]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            val menuProductEntity = MenuProductEntity[insertOrderProduct.menuProductUuid]
            OrderProductEntity.new {
                count = insertOrderProduct.count
                name = menuProductEntity.name
                newPrice = menuProductEntity.newPrice
                oldPrice = menuProductEntity.oldPrice
                utils = menuProductEntity.utils
                nutrition = menuProductEntity.nutrition
                description = menuProductEntity.description
                comboDescription = menuProductEntity.comboDescription
                photoLink = menuProductEntity.photoLink
                barcode = menuProductEntity.barcode
                menuProduct = menuProductEntity
                order = orderEntity
            }
        }

        orderEntity.toClientOrder()
    }

    override suspend fun getOrderListByCafeUuidLimited(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder> = query {
        OrderEntity.find {
            (OrderTable.cafe eq cafeUuid) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrder()
            }
    }

    override suspend fun getOrderListByCompanyUuidLimited(companyUuid: UUID, limitTime: Long): List<GetCafeOrder> =
        query {
            OrderEntity.find {
                (OrderTable.company eq companyUuid) and
                        (OrderTable.time greater limitTime)
            }.orderBy(OrderTable.time to SortOrder.DESC)
                .map { orderEntity ->
                    orderEntity.toCafeOrder()
                }
        }

    override suspend fun getOrderListByCafeUuid(cafeUuid: UUID, limitTimeMillis: Long): List<GetCafeOrder> = query {
        OrderEntity.find {
            OrderTable.cafe eq cafeUuid and OrderTable.time.greater(limitTimeMillis)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrder()
            }
    }

    override suspend fun getOrderListByCompanyUuid(companyUuid: UUID, limitTimeMillis: Long): List<GetCafeOrder> = query {
        val companyEntity = CompanyEntity.findById(companyUuid) ?: return@query emptyList()

        companyEntity.cities.flatMap { cityEntity ->
            cityEntity.cafes
        }.flatMap { cafeEntity ->
            cafeEntity.orders
        }.filter { orderEntity ->
            orderEntity.time > limitTimeMillis
        }.sortedByDescending { getCafeOrder ->
            getCafeOrder.time
        }.map { orderEntity ->
            orderEntity.toCafeOrder()
        }
    }

    override suspend fun getClientOrderByUuid(orderUuid: UUID): GetClientOrder? = query {
        OrderEntity.findById(orderUuid)?.toClientOrder()
    }

    override suspend fun getCafeOrderByUuid(orderUuid: UUID): GetCafeOrder? = query {
        OrderEntity.findById(orderUuid)?.toCafeOrder()
    }

    override suspend fun deleteCafeOrderByUuid(orderUuid: UUID): GetCafeOrder? = query {
        OrderEntity.findById(orderUuid)?.apply {
            oderProducts.onEach { orderProductEntity ->
                orderProductEntity.delete()
            }
            delete()
        }?.toCafeOrder()
    }

    override suspend fun updateOrderStatusByUuid(orderUuid: UUID, status: String): GetCafeOrder? = query {
        val orderEntity = OrderEntity.findById(orderUuid)
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