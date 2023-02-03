package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.*
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderUpdate
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.model.order.client.insert.InsertOrder
import com.bunbeauty.fooddelivery.data.model.order.client.insert.InsertOrderV2
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

    override suspend fun insertOrder(insertOrder: InsertOrderV2): GetClientOrderV2 = query {
        val orderEntity = OrderEntity.new {
            time = insertOrder.time
            isDelivery = insertOrder.isDelivery
            code = insertOrder.code
            addressDescription = insertOrder.address.description
            addressStreet = insertOrder.address.street
            addressHouse = insertOrder.address.house
            addressFlat = insertOrder.address.flat
            addressEntrance = insertOrder.address.entrance
            addressFloor = insertOrder.address.floor
            addressComment = insertOrder.address.comment
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

        orderEntity.toClientOrderV2()
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

    override suspend fun getOrderListByUserUuid(userUuid: UUID, count: Int?): List<GetClientOrder> = query {
        OrderEntity.find {
            (OrderTable.clientUser eq userUuid)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .let { orderEntityList ->
                if (count != null) {
                    orderEntityList.limit(count)
                } else {
                    orderEntityList
                }
            }
            .map { orderEntity ->
                orderEntity.toClientOrder()
            }
    }

    override suspend fun getOrderListByUserUuidV2(userUuid: UUID, count: Int?): List<GetClientOrderV2> = query {
        OrderEntity.find {
            (OrderTable.clientUser eq userUuid)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .let { orderEntityList ->
                if (count != null) {
                    orderEntityList.limit(count)
                } else {
                    orderEntityList
                }
            }
            .map { orderEntity ->
                orderEntity.toClientOrderV2()
            }
    }

    override suspend fun getClientOrderByUuidV2(userUuid: UUID, orderUuid: UUID): GetClientOrderV2? = query {
        val orderEntity = OrderEntity.findById(orderUuid)
        if (orderEntity?.clientUser?.id?.value == userUuid) {
            orderEntity.toClientOrderV2()
        } else {
            null
        }
    }

    override suspend fun getOrderByUuid(orderUuid: UUID): GetCafeOrderDetails? = query {
        OrderEntity.findById(orderUuid)?.toCafeOrderDetails()
    }

    override suspend fun getOrderByUuidV2(orderUuid: UUID): GetCafeOrderDetailsV2? = query {
        OrderEntity.findById(orderUuid)?.toCafeOrderDetailsV2()
    }

    override suspend fun getOrderListByCompanyUuidLimited(
        companyUuid: UUID,
        limitTime: Long,
    ): List<GetClientOrderV2> = query {
        OrderEntity.find {
            (OrderTable.company eq companyUuid) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toClientOrderV2()
            }
    }

    override suspend fun getOrderDetailsListByCafeUuid(
        cafeUuid: UUID,
        startTimeMillis: Long,
        endTimeMillis: Long,
    ): List<GetCafeOrderDetailsV2> = query {
        OrderEntity.find {
            OrderTable.cafe eq cafeUuid and
                    OrderTable.time.greaterEq(startTimeMillis) and
                    OrderTable.time.less(endTimeMillis)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toCafeOrderDetailsV2()
            }
    }

    override suspend fun getStatisticOrderListByCafeUuid(
        cafeUuid: UUID,
        startTimeMillis: Long,
        endTimeMillis: Long
    ): List<GetStatisticOrder> = query {
        StatisticOrderEntity.find {
            OrderTable.cafe eq cafeUuid and
                    OrderTable.time.greaterEq(startTimeMillis) and
                    OrderTable.time.less(endTimeMillis)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toStatisticOrder()
            }
    }

    override suspend fun getClientOrderByUuid(orderUuid: UUID): GetClientOrder? = query {
        OrderEntity.findById(orderUuid)?.toClientOrder()
    }

    override suspend fun getClientOrderUpdateByUuid(orderUuid: UUID): GetClientOrderUpdate? = query {
        OrderUpdateEntity.findById(orderUuid)?.toClientOrderUpdate()
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

}