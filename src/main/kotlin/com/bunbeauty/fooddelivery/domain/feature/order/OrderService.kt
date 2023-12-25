package com.bunbeauty.fooddelivery.domain.feature.order

import com.bunbeauty.fooddelivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.CODE_LETTERS
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_STEP
import com.bunbeauty.fooddelivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.fooddelivery.data.Constants.ORDER_KOD_KEY
import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.data.session.SessionHandler
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.*
import com.bunbeauty.fooddelivery.domain.feature.order.model.CalculatedOrderValues
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductLight
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.OrderInfo
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PatchOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrderUpdate
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.OrderInfoV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.PostOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetClientOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.PostOrderV3
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class OrderService(
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository,
    private val clientUserRepository: ClientUserRepository,
    private val menuProductRepository: MenuProductRepository,
    private val cafeRepository: CafeRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {

    private val codesCount = CODE_LETTERS.length * CODE_NUMBER_COUNT
    private val cafeSessionHandler: SessionHandler<GetCafeOrder> = SessionHandler()
    private val clientSessionHandler: SessionHandler<GetClientOrder> = SessionHandler()
    private val clientSessionHandlerV2: SessionHandler<GetClientOrderUpdate> = SessionHandler()

    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder {
        if (postOrder.orderProducts.isEmpty()) {
            emptyProductListIsError()
        }

        val orderInfo = createOrderInfo(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )
        val insertOrder = postOrder.mapPostOrder(orderInfo)
        val clientOrder = orderRepository.insertOrder(insertOrder)
        val cafeOrder = orderRepository.getCafeOrderByUuid(clientOrder.uuid)
        cafeSessionHandler.emitNewValue(cafeOrder?.cafeUuid, cafeOrder)

        sendNotification(cafeOrder)

        return clientOrder.mapOrder(clientOrder.calculatedOrderValues)
    }

    suspend fun createOrderV2(clientUserUuid: String, postOrder: PostOrderV2): GetClientOrderV2 {
        if (postOrder.orderProducts.isEmpty()) {
            emptyProductListIsError()
        }

        val orderInfo = createOrderInfoV2(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )
        val insertOrder = postOrder.mapPostOrderV2(orderInfo)
        val clientOrder = orderRepository.insertOrderV2(insertOrder)
        val cafeOrder = orderRepository.getCafeOrderByUuid(clientOrder.uuid)
        cafeSessionHandler.emitNewValue(cafeOrder?.cafeUuid, cafeOrder)

        sendNotification(cafeOrder)

        return clientOrder.mapOrderToV2(clientOrder.calculatedOrderValues)
    }

    suspend fun createOrderV3(clientUserUuid: String, postOrder: PostOrderV3): GetClientOrderV2 {
        if (postOrder.orderProducts.isEmpty()) {
            emptyProductListIsError()
        }

        val orderInfo = createOrderInfoV2(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )
        val insertOrder = postOrder.mapPostOrderV3(orderInfo)
        val clientOrder = orderRepository.insertOrderV3(insertOrder)
        val cafeOrder = orderRepository.getCafeOrderByUuid(clientOrder.uuid)
        cafeSessionHandler.emitNewValue(cafeOrder?.cafeUuid, cafeOrder)

        sendNotification(cafeOrder)

        return clientOrder.mapOrderToV2(clientOrder.calculatedOrderValues)
    }

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder> {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(ORDER_HISTORY_DAY_COUNT).millis
        return orderRepository.getOrderListByCafeUuidLimited(cafeUuid, limitTime)
    }

    suspend fun getOrderListByUserUuid(userUuid: String, count: Int?): List<GetClientOrder> {
        return orderRepository.getOrderListByUserUuid(userUuid, count)
    }

    suspend fun getOrderListByUserUuidV2(
        userUuid: String,
        count: Int?,
        orderUuid: String?,
    ): List<GetClientOrderV2> {
        return if (orderUuid == null) {
            orderRepository.getOrderListByUserUuidV2(userUuid, count)
        } else {
            orderRepository.getClientOrderByUuidV2(
                userUuid = userUuid,
                orderUuid = orderUuid
            )?.let { getClientOrderV2 ->
                listOf(getClientOrderV2)
            } ?: emptyList()
        }
    }

    suspend fun getOrderByUuid(uuid: String): GetCafeOrderDetails? {
        return orderRepository.getOrderByUuid(uuid)
    }

    suspend fun getOrderByUuidV2(uuid: String): GetCafeOrderDetailsV2? {
        return orderRepository.getOrderByUuidV2(uuid)
    }

    suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder? {
        val getCafeOrder = orderRepository.updateOrderStatusByUuid(
            orderUuid = orderUuid,
            status = patchOrder.status
        )
        cafeSessionHandler.emitNewValue(
            key = getCafeOrder?.cafeUuid,
            value = getCafeOrder
        )

        val getClientOrder = orderRepository.getClientOrderByUuid(orderUuid = orderUuid)
        clientSessionHandler.emitNewValue(
            key = getClientOrder?.clientUserUuid,
            value = getClientOrder
        )

        val getClientOrderUpdate = orderRepository.getClientOrderUpdateByUuid(orderUuid = orderUuid)
        clientSessionHandlerV2.emitNewValue(
            key = getClientOrderUpdate?.clientUserUuid,
            value = getClientOrderUpdate
        )

        return getCafeOrder
    }

    fun observeClientOrderUpdates(clientUserUuid: String): Flow<GetClientOrder> {
        return clientSessionHandler.connect(clientUserUuid)
    }

    fun observeClientOrderUpdatesV2(clientUserUuid: String): Flow<GetClientOrderUpdate> {
        return clientSessionHandlerV2.connect(clientUserUuid)
    }

    fun observeCafeOrderUpdates(cafeUuid: String): Flow<GetCafeOrder> {
        return cafeSessionHandler.connect(cafeUuid)
    }

    fun clientDisconnect(clientUserUuid: String) {
        clientSessionHandler.disconnect(clientUserUuid)
        clientSessionHandlerV2.disconnect(clientUserUuid)
    }

    fun userDisconnect(cafeUuid: String) {
        cafeSessionHandler.disconnect(cafeUuid)
    }

    private suspend fun createOrderInfo(postOrder: PostOrder, clientUserUuid: String): OrderInfo {
        val cafeUuid = if (postOrder.isDelivery) {
            val addressUuid = postOrder.addressUuid ?: noAddressUuidForDeliveryError()
            addressRepository.getAddressByUuid(addressUuid)
                .orThrowNotFoundByUuidError(uuid = addressUuid)
                .street
                .cafeUuid
        } else {
            postOrder.cafeUuid ?: noCafeUuidForPickupError()
        }
        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val deliveryCost = getDeliveryCost(
            isDelivery = postOrder.isDelivery,
            clientUserUuid = clientUserUuid,
            orderProductList = postOrder.orderProducts.map(mapPostOrderProductToLight)
        )

        return OrderInfo(
            time = DateTime.now().millis,
            code = generateCode(cafeUuid = cafeUuid),
            deliveryCost = deliveryCost,
            cafeUuid = cafeUuid,
            companyUuid = company.uuid,
            clientUserUuid = clientUserUuid,
        )
    }

    private suspend fun createOrderInfoV2(postOrder: PostOrderV2, clientUserUuid: String): OrderInfoV2 {
        val cafeUuid = if (postOrder.isDelivery) {
            val addressUuid = postOrder.address.uuid
            addressRepository.getAddressByUuid(uuid = addressUuid)
                .orThrowNotFoundByUuidError(uuid = addressUuid)
                .street
                .cafeUuid
        } else {
            postOrder.address.uuid
        }
        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val deliveryCost = getDeliveryCost(
            isDelivery = postOrder.isDelivery,
            clientUserUuid = clientUserUuid,
            orderProductList = postOrder.orderProducts.map(mapPostOrderProductToLight)
        )
        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }

        return OrderInfoV2(
            time = DateTime.now().millis,
            code = generateCode(cafeUuid = cafeUuid),
            deliveryCost = deliveryCost,
            percentDiscount = percentDiscount,
            cafeUuid = cafeUuid,
            companyUuid = company.uuid,
            clientUserUuid = clientUserUuid,
        )
    }

    private suspend fun createOrderInfoV2(postOrder: PostOrderV3, clientUserUuid: String): OrderInfoV2 {
        val cafeUuid = if (postOrder.isDelivery) {
            findCafeByAddressUuid(
                addressUuid = postOrder.address.uuid
            ).uuid
        } else {
            postOrder.address.uuid
        }
        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val deliveryCost = getDeliveryCost(
            isDelivery = postOrder.isDelivery,
            clientUserUuid = clientUserUuid,
            orderProductList = postOrder.orderProducts.map(mapPostOrderProductV3ToLight)
        )
        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }

        return OrderInfoV2(
            time = DateTime.now().millis,
            code = generateCode(cafeUuid = cafeUuid),
            deliveryCost = deliveryCost,
            percentDiscount = percentDiscount,
            cafeUuid = cafeUuid,
            companyUuid = company.uuid,
            clientUserUuid = clientUserUuid,
        )
    }

    private suspend fun findCafeByAddressUuid(addressUuid: String): Cafe {
        val address = addressRepository.getAddressV2ByUuid(uuid = addressUuid)
            .orThrowNotFoundByUuidError(uuid = addressUuid)
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid = address.cityUuid)

        return cafeList.firstOrNull() ?: noCafeError(addressUuid = addressUuid)
    }

    private suspend fun generateCode(cafeUuid: String): String {
        val codeCounter = cafeRepository.incrementCafeCodeCounter(
            cafeUuid = cafeUuid,
            limit = codesCount
        ) ?: codeCounterIncrementError(cafeUuid)

        val codeLetter = CODE_LETTERS[codeCounter % CODE_LETTERS.length].toString()
        val codeNumber = (codeCounter * CODE_NUMBER_STEP) % CODE_NUMBER_COUNT
        val codeNumberString = if (codeNumber < 10) {
            "0$codeNumber"
        } else {
            codeNumber.toString()
        }

        return codeLetter + CODE_DIVIDER + codeNumberString
    }

    private suspend fun getDeliveryCost(
        isDelivery: Boolean,
        clientUserUuid: String,
        orderProductList: List<OrderProductLight>,
    ): Int? {
        if (!isDelivery) {
            return null
        }

        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val orderCost = orderProductList.sumOf { postOrderProduct ->
            val menuProduct = menuProductRepository.getMenuProductByUuid(uuid = postOrderProduct.menuProductUuid)
            (menuProduct?.newPrice ?: 0) * postOrderProduct.count
        }
        return if (orderCost >= company.delivery.forFree) {
            0
        } else {
            company.delivery.cost
        }
    }

    private fun sendNotification(cafeOrder: GetCafeOrder?) {
        if (cafeOrder == null) {
            return
        }

        firebaseMessaging.send(
            Message.builder()
                .putData(ORDER_KOD_KEY, cafeOrder.code)
                .setTopic(cafeOrder.cafeUuid)
                .build()
        )
    }

    private val Order.calculatedOrderValues: CalculatedOrderValues
        get() = CalculatedOrderValues(
            oldTotalCost = oldTotalCost,
            newTotalCost = newTotalCost,
        )

    private val Order.newTotalCost: Int
        get() {
            val oderProductsSumCost = oderProducts.sumOf { orderProductEntity ->
                orderProductEntity.count * orderProductEntity.newPrice
            }
            val discount = (oderProductsSumCost * (percentDiscount ?: 0) / 100.0).toInt()

            return oderProductsSumCost - discount + (deliveryCost ?: 0)
        }

    private val Order.oldTotalCost: Int?
        get() {
            val isOldTotalCostEnabled = oderProducts.any { orderProductEntity ->
                orderProductEntity.oldPrice != null
            } || percentDiscount != null
            return if (isOldTotalCostEnabled) {
                oderProducts.sumOf { orderProductEntity ->
                    orderProductEntity.count * (orderProductEntity.oldPrice ?: orderProductEntity.newPrice)
                } + (deliveryCost ?: 0)
            } else {
                null
            }
        }

    private fun emptyProductListIsError(): Nothing {
        error("Product list is empty")
    }

    private fun noAddressUuidForDeliveryError(): Nothing {
        error("No addressUuid for delivery")
    }

    private fun noCafeUuidForPickupError(): Nothing {
        error("No cafeUuid for pickup")
    }

    private fun codeCounterIncrementError(cafeUuid: String): Nothing {
        error("Failed to increment code counter - Cafe($cafeUuid)")
    }

    private fun noCafeError(addressUuid: String): Nothing {
        error("no cafe associated with address ($addressUuid)")
    }

}