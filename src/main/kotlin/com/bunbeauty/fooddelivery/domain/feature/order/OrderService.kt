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
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.*
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
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
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductsNewCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderTotalUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CheckIsPointInPolygonUseCase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime

class OrderService(
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository,
    private val clientUserRepository: ClientUserRepository,
    private val menuProductRepository: MenuProductRepository,
    private val cafeRepository: CafeRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val checkIsPointInPolygonUseCase: CheckIsPointInPolygonUseCase,
    private val calculateOrderTotalUseCase: CalculateOrderTotalUseCase,
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase,
) {

    private val codesCount = CODE_LETTERS.length * CODE_NUMBER_COUNT

    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder {
        if (postOrder.orderProducts.isEmpty()) {
            emptyProductListIsError()
        }

        val orderInfo = createOrderInfo(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )
        val insertOrder = postOrder.mapPostOrder(orderInfo)
        val order = orderRepository.insertOrder(insertOrder)
        orderRepository.updateSession(
            key = order.cafeWithCity.cafe.uuid,
            order = order
        )

        sendNotification(order)

        val orderTotal = calculateOrderTotalUseCase(order)
        return order.mapOrder(orderTotal)
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
        val order = orderRepository.insertOrderV2(insertOrder)
        orderRepository.updateSession(
            key = order.cafeWithCity.cafe.uuid,
            order = order
        )

        sendNotification(order)

        val orderTotal = calculateOrderTotalUseCase(order)
        return order.mapOrderToV2(orderTotal)
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
        val order = orderRepository.insertOrderV3(insertOrder)
        orderRepository.updateSession(
            key = order.cafeWithCity.cafe.uuid,
            order = order
        )

        sendNotification(order)

        val orderTotal = calculateOrderTotalUseCase(order)
        return order.mapOrderToV2(orderTotal)
    }

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder> {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(ORDER_HISTORY_DAY_COUNT).millis
        return orderRepository.getOrderListByCafeUuidLimited(
            cafeUuid = cafeUuid,
            limitTime = limitTime
        ).map(mapOrderToCafeOrder)
    }

    suspend fun getOrderListByUserUuid(userUuid: String, count: Int?): List<GetClientOrder> {
        return orderRepository.getOrderListByUserUuid(
            userUuid = userUuid,
            count = count
        ).map { order ->
            val orderTotal = calculateOrderTotalUseCase(order)
            order.mapOrder(orderTotal)
        }
    }

    suspend fun getOrderListByUserUuidV2(
        userUuid: String,
        count: Int?,
        orderUuid: String?,
    ): List<GetClientOrderV2> {
        val orderEntityList = if (orderUuid == null) {
            orderRepository.getOrderListByUserUuid(
                userUuid = userUuid,
                count = count
            )
        } else {
            orderRepository.getOrderByUuid(orderUuid = orderUuid)
                ?.takeIf { order ->
                    order.clientUser.uuid == userUuid
                }?.let { order ->
                    listOf(order)
                }.orEmpty()
        }

        return orderEntityList.map { order ->
            val orderTotal = calculateOrderTotalUseCase(order)
            order.mapOrderToV2(orderTotal)
        }
    }

    suspend fun getOrderByUuid(uuid: String): GetCafeOrderDetails {
        val order = orderRepository.getOrderByUuid(uuid).orThrowNotFoundByUuidError(uuid)
        val orderTotal = calculateOrderTotalUseCase(order)

        return order.mapOrderToCafeOrderDetails(orderTotal)
    }

    suspend fun getOrderByUuidV2(uuid: String): GetCafeOrderDetailsV2 {
        val order = orderRepository.getOrderByUuid(uuid).orThrowNotFoundByUuidError(uuid)
        val orderTotal = calculateOrderTotalUseCase(order)

        return order.mapOrderToCafeOrderDetailsV2(orderTotal)
    }

    suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder {
        val order = orderRepository.updateOrderStatusByUuid(
            orderUuid = orderUuid,
            status = patchOrder.status
        ).orThrowNotFoundByUuidError(orderUuid)

        orderRepository.updateSession(
            key = order.cafeWithCity.cafe.uuid,
            order = order
        )
        orderRepository.updateSession(
            key = order.clientUser.uuid,
            order = order
        )

        return order.mapOrderToCafeOrder()
    }

    fun observeClientOrderUpdates(clientUserUuid: String): Flow<GetClientOrder> {
        return orderRepository.getOrderFlowByKey(clientUserUuid).map { order ->
            val orderTotal = calculateOrderTotalUseCase(order)
            order.mapOrder(orderTotal)
        }
    }

    fun observeClientOrderUpdatesV2(clientUserUuid: String): Flow<GetClientOrderUpdate> {
        return orderRepository.getOrderFlowByKey(clientUserUuid).map { order ->
            GetClientOrderUpdate(
                uuid = order.uuid,
                status = order.status,
                clientUserUuid = order.clientUser.uuid,
            )
        }
    }

    fun observeCafeOrderUpdates(cafeUuid: String): Flow<GetCafeOrder> {
        return orderRepository.getOrderFlowByKey(cafeUuid).map(mapOrderToCafeOrder)
    }

    fun clientDisconnect(clientUserUuid: String) {
        orderRepository.disconnectFromSession(clientUserUuid)
    }

    fun userDisconnect(cafeUuid: String) {
        orderRepository.disconnectFromSession(cafeUuid)
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
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductByUuid(postOrderProduct.menuProductUuid)
                    .orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
                postOrderProduct.mapPostOrderProductToOrderProduct(menuProduct)
            },
            percentDiscount = null,
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
        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }
        val deliveryCost = getDeliveryCost(
            isDelivery = postOrder.isDelivery,
            clientUserUuid = clientUserUuid,
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductByUuid(postOrderProduct.menuProductUuid)
                    .orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
                postOrderProduct.mapPostOrderProductToOrderProduct(menuProduct)
            },
            percentDiscount = percentDiscount
        )

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

    private suspend fun createOrderInfoV2(
        postOrder: PostOrderV3,
        clientUserUuid: String,
    ): OrderInfoV2 {
        val cafeUuid = if (postOrder.isDelivery) {
            val addressUuid = postOrder.address.uuid
            findCafeByAddressUuid(addressUuid = addressUuid).uuid
        } else {
            postOrder.address.uuid
        }
        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }
        val deliveryCost = getDeliveryCost(
            isDelivery = postOrder.isDelivery,
            clientUserUuid = clientUserUuid,
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductByUuid(postOrderProduct.menuProductUuid)
                    .orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
                postOrderProduct.mapPostOrderProductV3ToOrderProduct(menuProduct)
            },
            percentDiscount = percentDiscount
        )

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
        val addressV1 = addressRepository.getAddressByUuid(uuid = addressUuid)
        return if (addressV1 != null) {
            findCafeByCoordinates(
                addressUuid = addressUuid,
                cityUuid = addressV1.cityUuid,
                latitude = addressV1.street.latitude,
                longitude = addressV1.street.longitude,
            )
        } else {
            val addressV2 = addressRepository.getAddressV2ByUuid(uuid = addressUuid)
                .orThrowNotFoundByUuidError(uuid = addressUuid)
            findCafeByCoordinates(
                addressUuid = addressUuid,
                cityUuid = addressV2.cityUuid,
                latitude = addressV2.street.latitude,
                longitude = addressV2.street.longitude,
            )
        }
    }

    private suspend fun findCafeByCoordinates(
        addressUuid: String,
        cityUuid: String,
        latitude: Double,
        longitude: Double,
    ): Cafe {
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid = cityUuid)
        return cafeList.find { cafe ->
            isCafeAvailableAtCoordinates(
                cafe = cafe,
                latitude = latitude,
                longitude = longitude,
            )
        } ?: noCafeError(addressUuid = addressUuid)
    }

    private fun isCafeAvailableAtCoordinates(
        cafe: Cafe,
        latitude: Double,
        longitude: Double,
    ): Boolean {
        return cafe.isVisible && cafe.zones.any { zone ->
            zone.isVisible && checkIsPointInPolygonUseCase(
                latitude = latitude,
                longitude = longitude,
                polygon = zone.points.map { point ->
                    point.latitude to point.longitude
                },
            )
        }
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
        orderProducts: List<OrderProduct>,
        percentDiscount: Int?,
    ): Int? {
        if (!isDelivery) {
            return null
        }

        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = orderProducts,
            percentDiscount = percentDiscount
        )

        return if (orderProductsNewCost >= company.delivery.forFree) {
            0
        } else {
            company.delivery.cost
        }
    }

    private fun sendNotification(order: Order) {
        firebaseMessaging.send(
            Message.builder()
                .putData(ORDER_KOD_KEY, order.code)
                .setTopic(order.cafeWithCity.cafe.uuid)
                .build()
        )
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