package com.bunbeauty.fooddelivery.domain.feature.order

import com.bunbeauty.fooddelivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.CODE_LETTERS
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_STEP
import com.bunbeauty.fooddelivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.errorWithCode
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZoneWithCafe
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapLightOrderToGetCafeOrder
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrder
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrderToCafeOrder
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrderToCafeOrderDetails
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrderToCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrderToV2
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapPostOrder
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapPostOrderProductToOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapPostOrderProductV3ToOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapPostOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapPostOrderV3
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderAvailability
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
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderTotalUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.FindDeliveryZoneByCityUuidAndCoordinatesUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.GetDeliveryCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.IsOrderAvailableUseCase
import com.bunbeauty.fooddelivery.service.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

private const val CAFE_IS_CLOSED_CODE = 901

class OrderService(
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository,
    private val menuProductRepository: MenuProductRepository,
    private val cafeRepository: CafeRepository,
    private val notificationService: NotificationService,
    private val findDeliveryZoneByCityUuidAndCoordinatesUseCase: FindDeliveryZoneByCityUuidAndCoordinatesUseCase,
    private val calculateOrderTotalUseCase: CalculateOrderTotalUseCase,
    private val getDeliveryCostUseCase: GetDeliveryCostUseCase,
    private val isOrderAvailableUseCase: IsOrderAvailableUseCase,
    private val companyRepository: CompanyRepository
) : CoroutineScope {

    private val codesCount = CODE_LETTERS.length * CODE_NUMBER_COUNT

    override val coroutineContext: CoroutineContext = SupervisorJob()

    private fun getTime(time: LocalTime): String {
        // Получаем текущее время

        // Определяем формат вывода
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        // Форматируем время и выводим его
        val formattedTime = time.format(formatter)
        return formattedTime
    }

    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder {
        if (postOrder.orderProducts.isEmpty()) {
            productListIsEmptyError()
        }

        val orderInfo = createOrderInfo(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )

        if (!isOrderAvailableUseCase(companyUuid = orderInfo.companyUuid)) {
            cafeIsClosedError()
        }

        val insertOrder = postOrder.mapPostOrder(orderInfo)

        val order = orderRepository.insertOrder(insertOrder)

        orderRepository.updateSession(
            key = order.cafeWithCity.cafeWithZones.uuid,
            order = order
        )

        notificationService.sendNotification(
            cafeUuid = orderInfo.cafeUuid,
            orderCode = order.code
        )

        val orderTotal = calculateOrderTotalUseCase(order)

        return order.mapOrder(orderTotal)
    }

    suspend fun createOrderV2(clientUserUuid: String, postOrder: PostOrderV2): GetClientOrderV2 {
        if (postOrder.orderProducts.isEmpty()) {
            productListIsEmptyError()
        }

        val orderInfo = createOrderInfoV2(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )

        if (!isOrderAvailableUseCase(companyUuid = orderInfo.companyUuid)) {
            cafeIsClosedError()
        }

        val insertOrder = postOrder.mapPostOrderV2(orderInfo)
        val order = orderRepository.insertOrderV2(insertOrder)
        orderRepository.updateSession(
            key = order.cafeWithCity.cafeWithZones.uuid,
            order = order
        )

        notificationService.sendNotification(
            cafeUuid = orderInfo.cafeUuid,
            orderCode = order.code
        )

        val orderTotal = calculateOrderTotalUseCase(order)
        return order.mapOrderToV2(orderTotal)
    }

    suspend fun createOrderV3(clientUserUuid: String, postOrder: PostOrderV3): GetClientOrderV2 {
        if (postOrder.orderProducts.isEmpty()) {
            productListIsEmptyError()
        }

        val orderInfo = createOrderInfoV2(
            postOrder = postOrder,
            clientUserUuid = clientUserUuid
        )

        if (!isOrderAvailableUseCase(companyUuid = orderInfo.companyUuid)) {
            cafeIsClosedError()
        }

        val insertOrder = postOrder.mapPostOrderV3(orderInfo)

        val order = orderRepository.insertOrderV3(insertOrder)

        orderRepository.updateSession(
            key = order.cafeWithCity.cafeWithZones.uuid,
            order = order
        )

        notificationService.sendNotification(
            cafeUuid = orderInfo.cafeUuid,
            orderCode = order.code
        )

        val orderTotal = calculateOrderTotalUseCase(order)

        return order.mapOrderToV2(orderTotal)
    }

    suspend fun getOrderListByCafeUuid2(cafeUuid: String): List<GetCafeOrder> {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(ORDER_HISTORY_DAY_COUNT).millis
        return orderRepository.getLightOrder(
            cafeUuid = cafeUuid,
            limitTime = limitTime
        ).map(mapLightOrderToGetCafeOrder)
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
        orderUuid: String?
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
            key = order.cafeWithCity.cafeWithZones.uuid,
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
                clientUserUuid = order.clientUser.uuid
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

    suspend fun getOrderAvailability(companyUuid: String): OrderAvailability {
        return OrderAvailability(
            isAvailable = isOrderAvailableUseCase(companyUuid = companyUuid)
        )
    }

    private suspend fun createOrderInfo(postOrder: PostOrder, clientUserUuid: String): OrderInfo {
        val deliveryZoneWithCafe: DeliveryZoneWithCafe?
        val cafeUuid: String
        if (postOrder.isDelivery) {
            val addressUuid = postOrder.addressUuid ?: noAddressUuidForDeliveryError()
            deliveryZoneWithCafe = findDeliveryZone(addressUuid = addressUuid)
            cafeUuid = deliveryZoneWithCafe.cafeWithZones.uuid
        } else {
            deliveryZoneWithCafe = null
            cafeUuid = postOrder.cafeUuid ?: noCafeUuidForPickupError()
        }

        val company = companyRepository.getCompanyByUserUuid(userUuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = postOrder.isDelivery,
            deliveryZone = deliveryZoneWithCafe?.deliveryZone,
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductByUuid(
                    companyUuid = company.uuid,
                    uuid = postOrderProduct.menuProductUuid
                ).orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
                postOrderProduct.mapPostOrderProductToOrderProduct(menuProduct)
            },
            percentDiscount = null,
            company = company
        )

        return OrderInfo(
            time = DateTime.now().millis,
            code = generateCode(cafeUuid = cafeUuid),
            deliveryCost = deliveryCost,
            cafeUuid = cafeUuid,
            companyUuid = company.uuid,
            clientUserUuid = clientUserUuid
        )
    }

    private suspend fun createOrderInfoV2(postOrder: PostOrderV2, clientUserUuid: String): OrderInfoV2 {
        val deliveryZoneWithCafe: DeliveryZoneWithCafe?
        val cafeUuid: String
        if (postOrder.isDelivery) {
            deliveryZoneWithCafe = findDeliveryZone(addressUuid = postOrder.address.uuid)
            cafeUuid = deliveryZoneWithCafe.cafeWithZones.uuid
        } else {
            deliveryZoneWithCafe = null
            cafeUuid = postOrder.address.uuid
        }

        val company = companyRepository.getCompanyByUserUuid(userUuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)

        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = postOrder.isDelivery,
            deliveryZone = deliveryZoneWithCafe?.deliveryZone,
            company = company,
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductByUuid(
                    companyUuid = company.uuid,
                    uuid = postOrderProduct.menuProductUuid
                ).orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
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
            clientUserUuid = clientUserUuid
        )
    }

    private suspend fun createOrderInfoV2(
        postOrder: PostOrderV3,
        clientUserUuid: String
    ): OrderInfoV2 {
        val deliveryZoneWithCafe: DeliveryZoneWithCafe?
        val cafeUuid: String

        if (postOrder.isDelivery) {
            deliveryZoneWithCafe = findDeliveryZone(addressUuid = postOrder.address.uuid)
            cafeUuid = deliveryZoneWithCafe.cafeWithZones.uuid
        } else {
            deliveryZoneWithCafe = null
            cafeUuid = postOrder.address.uuid
        }

        val company = companyRepository.getCompanyByUserUuid(userUuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)

        val percentDiscount = company.percentDiscount?.takeIf {
            val orderCount = orderRepository.getOrderCountByUserUuid(userUuid = clientUserUuid)
            orderCount == 0L
        }

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = postOrder.isDelivery,
            deliveryZone = deliveryZoneWithCafe?.deliveryZone,
            company = company,
            orderProducts = postOrder.orderProducts.map { postOrderProduct ->
                val menuProduct = menuProductRepository.getMenuProductWithAdditionListByUuid(
                    companyUuid = company.uuid,
                    uuid = postOrderProduct.menuProductUuid
                ).orThrowNotFoundByUuidError(postOrderProduct.menuProductUuid)
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
            clientUserUuid = clientUserUuid
        )
    }

    private suspend fun findDeliveryZone(addressUuid: String): DeliveryZoneWithCafe {
        val addressV1 = addressRepository.getAddressByUuid(uuid = addressUuid)
        return if (addressV1 != null) {
            findDeliveryZoneByCityUuidAndCoordinatesUseCase(
                cityUuid = addressV1.cityUuid,
                latitude = addressV1.street.latitude,
                longitude = addressV1.street.longitude,
                addressUuid = addressUuid
            )
        } else {
            val addressV2 = addressRepository.getAddressV2ByUuid(uuid = addressUuid)
                .orThrowNotFoundByUuidError(uuid = addressUuid)
            findDeliveryZoneByCityUuidAndCoordinatesUseCase(
                cityUuid = addressV2.cityUuid,
                latitude = addressV2.street.latitude,
                longitude = addressV2.street.longitude,
                addressUuid = addressUuid
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

    private fun productListIsEmptyError(): Nothing {
        error("Product list is empty")
    }

    private fun cafeIsClosedError(): Nothing {
        errorWithCode(
            message = "Cafe is closed",
            code = CAFE_IS_CLOSED_CODE
        )
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
}
