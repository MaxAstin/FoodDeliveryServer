package com.bunbeauty.fooddelivery.service.order

import com.bunbeauty.fooddelivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.CODE_LETTERS
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_STEP
import com.bunbeauty.fooddelivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.fooddelivery.data.Constants.ORDER_KOD_KEY
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.order.*
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import com.bunbeauty.fooddelivery.data.session.SessionHandler
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kotlinx.coroutines.flow.SharedFlow
import org.joda.time.DateTime

class OrderService(
    private val orderRepository: IOrderRepository,
    private val streetRepository: IStreetRepository,
    private val clientUserRepository: IClientUserRepository,
    private val menuProductRepository: IMenuProductRepository,
    private val cafeRepository: ICafeRepository,
    private val firebaseMessaging: FirebaseMessaging,
) : IOrderService {

    val codesCount = CODE_LETTERS.length * CODE_NUMBER_COUNT
    val cafeSessionHandler: SessionHandler<GetCafeOrder> = SessionHandler()
    val clientSessionHandler: SessionHandler<GetClientOrder> = SessionHandler()

    override suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder? {
        val currentMillis = DateTime.now().millis
        val cafeUuid = if (postOrder.isDelivery) {
            val addressUuid = postOrder.addressUuid?.toUuid()
            if (addressUuid == null) {
                null
            } else {
                streetRepository.getStreetByAddressUuid(addressUuid)?.cafeUuid
            }
        } else {
            postOrder.cafeUuid
        } ?: return null
        val cafeCodeCounter = getNewOrderCode(cafeUuid) ?: return null

        val company = clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())?.company ?: return null

        val deliveryCost = getDeliveryCost(postOrder.isDelivery, postOrder.orderProducts, company)
        val deferredTime = postOrder.deferredTime?.let { deferredTime ->
            if (postOrder.deferredTime < 0) {
                null
            } else {
                deferredTime
            }
        }

        val insertOrder = InsertOrder(
            time = currentMillis,
            isDelivery = postOrder.isDelivery,
            code = generateCode(cafeCodeCounter),
            addressDescription = postOrder.addressDescription,
            comment = postOrder.comment,
            deferredTime = deferredTime,
            status = OrderStatus.NOT_ACCEPTED.name,
            deliveryCost = deliveryCost,
            cafeUuid = cafeUuid.toUuid(),
            companyUuid = company.uuid.toUuid(),
            clientUserUuid = clientUserUuid.toUuid(),
            orderProductList = postOrder.orderProducts.map { postOrderProduct ->
                InsertOrderProduct(
                    menuProductUuid = postOrderProduct.menuProductUuid.toUuid(),
                    count = postOrderProduct.count
                )
            },
        )
        val clientOrder = orderRepository.insertOrder(insertOrder)
        val cafeOrder = orderRepository.getCafeOrderByUuid(clientOrder.uuid.toUuid())
        cafeSessionHandler.emitNewValue(cafeOrder?.cafeUuid, cafeOrder)

        sendNotification(cafeOrder)

        return clientOrder
    }

    override suspend fun createOrder(clientUserUuid: String, postOrder: PostOrderV2): GetClientOrderV2? {
        val currentMillis = DateTime.now().millis
        val cafeUuid = if (postOrder.isDelivery) {
            streetRepository.getStreetByAddressUuid(postOrder.address.uuid.toUuid())?.cafeUuid
        } else {
            postOrder.address.uuid
        } ?: return null
        val cafeCodeCounter = getNewOrderCode(cafeUuid) ?: return null

        val company = clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())?.company ?: return null

        val deliveryCost = getDeliveryCost(postOrder.isDelivery, postOrder.orderProducts, company)
        val deferredTime = postOrder.deferredTime?.let { deferredTime ->
            if (postOrder.deferredTime < 0) {
                null
            } else {
                deferredTime
            }
        }

        val insertOrder = InsertOrderV2(
            time = currentMillis,
            isDelivery = postOrder.isDelivery,
            code = generateCode(cafeCodeCounter),
            address = InsertOrderAddress(
                description = postOrder.address.description,
                street = postOrder.address.street,
                house = postOrder.address.house,
                flat = postOrder.address.flat,
                entrance = postOrder.address.entrance,
                floor = postOrder.address.floor,
                comment = postOrder.address.comment,
            ),
            comment = postOrder.comment,
            deferredTime = deferredTime,
            status = OrderStatus.NOT_ACCEPTED.name,
            deliveryCost = deliveryCost,
            cafeUuid = cafeUuid.toUuid(),
            companyUuid = company.uuid.toUuid(),
            clientUserUuid = clientUserUuid.toUuid(),
            orderProductList = postOrder.orderProducts.map { postOrderProduct ->
                InsertOrderProduct(
                    menuProductUuid = postOrderProduct.menuProductUuid.toUuid(),
                    count = postOrderProduct.count
                )
            },
        )
        val clientOrder = orderRepository.insertOrder(insertOrder)
        val cafeOrder = orderRepository.getCafeOrderByUuid(clientOrder.uuid.toUuid())
        cafeSessionHandler.emitNewValue(cafeOrder?.cafeUuid, cafeOrder)

        sendNotification(cafeOrder)

        return clientOrder
    }

    override suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder> {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(ORDER_HISTORY_DAY_COUNT).millis
        return orderRepository.getOrderListByCafeUuidLimited(cafeUuid.toUuid(), limitTime)
    }

    override suspend fun getOrderListByUserUuid(userUuid: String, count: Int?): List<GetClientOrder> {
        return orderRepository.getOrderListByUserUuid(userUuid.toUuid(), count)
    }

    override suspend fun getOrderListByUserUuidV2(userUuid: String, count: Int?): List<GetClientOrderV2> {
        return orderRepository.getOrderListByUserUuidV2(userUuid.toUuid(), count)
    }

    override suspend fun getOrderByUuid(uuid: String): GetCafeOrderDetails? {
        return orderRepository.getOrderByUuid(uuid.toUuid())
    }

    override suspend fun getOrderByUuidV2(uuid: String): GetCafeOrderDetailsV2? {
        return orderRepository.getOrderByUuidV2(uuid.toUuid())
    }

    override suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder? {
        val getCafeOrder = orderRepository.updateOrderStatusByUuid(orderUuid.toUuid(), patchOrder.status)

        val getClientOrder = orderRepository.getClientOrderByUuid(orderUuid.toUuid())
        clientSessionHandler.emitNewValue(getClientOrder?.clientUserUuid, getClientOrder)
        cafeSessionHandler.emitNewValue(getCafeOrder?.cafeUuid, getCafeOrder)

        return getCafeOrder
    }

    override suspend fun deleteOrder(orderUuid: String): GetCafeOrder? {
        return orderRepository.deleteCafeOrderByUuid(orderUuid.toUuid())
    }

    override suspend fun observeClientOrderUpdates(clientUserUuid: String): SharedFlow<GetClientOrder> {
        return clientSessionHandler.connect(clientUserUuid)
    }

    override suspend fun observeCafeOrderUpdates(cafeUuid: String): SharedFlow<GetCafeOrder> {
        return cafeSessionHandler.connect(cafeUuid)
    }

    override fun clientDisconnect(clientUserUuid: String) {
        clientSessionHandler.disconnect(clientUserUuid)
    }

    override fun userDisconnect(cafeUuid: String) {
        cafeSessionHandler.disconnect(cafeUuid)
    }

    fun generateCode(codeCounter: Int): String {
        val codeLetter = CODE_LETTERS[codeCounter % CODE_LETTERS.length].toString()
        val codeNumber = (codeCounter * CODE_NUMBER_STEP) % CODE_NUMBER_COUNT
        val codeNumberString = if (codeNumber < 10) {
            "0$codeNumber"
        } else {
            codeNumber.toString()
        }

        return codeLetter + CODE_DIVIDER + codeNumberString
    }

    suspend fun getDeliveryCost(
        isDelivery: Boolean,
        orderProductList: List<PostOrderProduct>,
        company: GetCompany,
    ): Int? {
        if (!isDelivery) {
            return null
        }
        val orderCost = orderProductList.sumOf { postOrderProduct ->
            val menuProduct = menuProductRepository.getMenuProductByUuid(postOrderProduct.menuProductUuid.toUuid())
            (menuProduct?.newPrice ?: 0) * postOrderProduct.count
        }
        return if (orderCost >= company.delivery.forFree) {
            0
        } else {
            company.delivery.cost
        }
    }

    fun sendNotification(cafeOrder: GetCafeOrder?) {
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

    suspend fun getNewOrderCode(cafeUuid: String): Int? {
        return cafeRepository.incrementCafeCodeCounter(cafeUuid.toUuid(), codesCount)
    }
}