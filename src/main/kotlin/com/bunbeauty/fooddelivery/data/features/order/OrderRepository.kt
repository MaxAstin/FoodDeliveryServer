package com.bunbeauty.fooddelivery.data.features.order

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StatisticOrderEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.AdditionEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.order.OrderEntity
import com.bunbeauty.fooddelivery.data.entity.order.OrderProductAdditionEntity
import com.bunbeauty.fooddelivery.data.entity.order.OrderProductEntity
import com.bunbeauty.fooddelivery.data.features.order.mapper.mapOrderEntity
import com.bunbeauty.fooddelivery.data.session.SessionHandler
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.InsertOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.InsertOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.InsertOrderV3
import com.bunbeauty.fooddelivery.domain.model.order.cafe.GetStatisticOrder
import com.bunbeauty.fooddelivery.domain.toUuid
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and

class OrderRepository {

    private val sessionHandler: SessionHandler<Order> = SessionHandler()

    suspend fun insertOrder(insertOrder: InsertOrder): Order = query {
        val orderEntity = OrderEntity.new {
            time = insertOrder.time
            isDelivery = insertOrder.isDelivery
            code = insertOrder.code
            addressDescription = insertOrder.addressDescription
            comment = insertOrder.comment
            deferredTime = insertOrder.deferredTime
            status = insertOrder.status
            deliveryCost = insertOrder.deliveryCost
            cafe = CafeEntity[insertOrder.cafeUuid.toUuid()]
            company = CompanyEntity[insertOrder.companyUuid.toUuid()]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid.toUuid()]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            val menuProductEntity = MenuProductEntity[insertOrderProduct.menuProductUuid.toUuid()]
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

        orderEntity.mapOrderEntity()
    }

    suspend fun insertOrderV2(insertOrder: InsertOrderV2): Order = query {
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
            paymentMethod = insertOrder.paymentMethod
            percentDiscount = insertOrder.percentDiscount
            cafe = CafeEntity[insertOrder.cafeUuid.toUuid()]
            company = CompanyEntity[insertOrder.companyUuid.toUuid()]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid.toUuid()]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            val menuProductEntity = MenuProductEntity[insertOrderProduct.menuProductUuid.toUuid()]
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

        orderEntity.mapOrderEntity()
    }

    suspend fun insertOrderV3(insertOrder: InsertOrderV3): Order = query {
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
            paymentMethod = insertOrder.paymentMethod
            percentDiscount = insertOrder.percentDiscount
            cafe = CafeEntity[insertOrder.cafeUuid.toUuid()]
            company = CompanyEntity[insertOrder.companyUuid.toUuid()]
            clientUser = ClientUserEntity[insertOrder.clientUserUuid.toUuid()]
        }
        insertOrder.orderProductList.onEach { insertOrderProduct ->
            val menuProductEntity = MenuProductEntity[insertOrderProduct.menuProductUuid.toUuid()]
            val orderProductEntity = OrderProductEntity.new {
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
            insertOrderProduct.additionUuids.forEach { additionUuid ->
                val additionEntity = AdditionEntity[additionUuid.toUuid()]
                OrderProductAdditionEntity.new {
                    name = additionEntity.fullName ?: additionEntity.name
                    price = additionEntity.price
                    orderProduct = orderProductEntity
                }
            }
        }

        orderEntity.mapOrderEntity()
    }

    suspend fun getOrderListByCafeUuidLimited(cafeUuid: String, limitTime: Long): List<Order> = query {
        OrderEntity.find {
            (OrderTable.cafe eq cafeUuid.toUuid()) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map(mapOrderEntity)
    }

    suspend fun getOrderListByUserUuid(userUuid: String, count: Int?): List<Order> = query {
        OrderEntity.find {
            (OrderTable.clientUser eq userUuid.toUuid())
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .let { orderEntityList ->
                if (count != null) {
                    orderEntityList.limit(count)
                } else {
                    orderEntityList
                }
            }
            .map(mapOrderEntity)
    }

    suspend fun getOrderCountByUserUuid(userUuid: String): Long = query {
        OrderEntity.find {
            OrderTable.clientUser eq userUuid.toUuid()
        }.count()
    }

    suspend fun getOrderByUuid(orderUuid: String): Order? = query {
        OrderEntity.findById(orderUuid.toUuid())?.mapOrderEntity()
    }

    suspend fun getOrderListByCompanyUuidLimited(
        companyUuid: String,
        limitTime: Long,
    ): List<Order> = query {
        OrderEntity.find {
            (OrderTable.company eq companyUuid.toUuid()) and
                    (OrderTable.time greater limitTime)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map(mapOrderEntity)
    }

    suspend fun getOrderDetailsListByCafeUuid(
        cafeUuid: String,
        startTimeMillis: Long,
        endTimeMillis: Long,
    ): List<Order> = query {
        OrderEntity.find {
            (OrderTable.cafe eq cafeUuid.toUuid()) and
                    OrderTable.time.greaterEq(startTimeMillis) and
                    OrderTable.time.less(endTimeMillis)
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map(mapOrderEntity)
    }

    suspend fun getStatisticOrderListByCafeUuid(
        cafeUuid: String,
        startTimeMillis: Long,
        endTimeMillis: Long,
    ): List<GetStatisticOrder> = query {
        StatisticOrderEntity.find {
            (OrderTable.cafe eq cafeUuid.toUuid()) and
                    OrderTable.time.greaterEq(startTimeMillis) and
                    OrderTable.time.less(endTimeMillis) and
                    (OrderTable.status eq "DELIVERED")
        }.orderBy(OrderTable.time to SortOrder.DESC)
            .map { orderEntity ->
                orderEntity.toStatisticOrder()
            }
    }

    suspend fun updateOrderStatusByUuid(orderUuid: String, status: String): Order? = query {
        val orderEntity = OrderEntity.findById(orderUuid.toUuid())
        orderEntity?.status = status
        orderEntity?.mapOrderEntity()
    }

    suspend fun updateSession(key: String, order: Order) {
        sessionHandler.emitNewValue(key, order)
    }

    fun getOrderFlowByKey(key: String): Flow<Order> {
        return sessionHandler.connect(key)
    }

    fun disconnectFromSession(key: String) {
        sessionHandler.disconnect(key)
    }

}