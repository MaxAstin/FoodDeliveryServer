package com.bunbeauty.fooddelivery.service.order

import com.bunbeauty.fooddelivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.CODE_LETTERS
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.fooddelivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.fooddelivery.data.Constants.ORDER_KOD_KEY
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.*
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
    private val firebaseMessaging: FirebaseMessaging,
) : IOrderService {

    val cafeSessionHandler: SessionHandler<GetCafeOrder> = SessionHandler()
    val clientSessionHandler: SessionHandler<GetClientOrder> = SessionHandler()

    override suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder? {
        val currentMillis = DateTime.now().millis
        val cafeUuid = if (postOrder.isDelivery) {
            val addressUuid = postOrder.addressUuid?.toUuid()
            if (addressUuid == null) {
                null
            } else {
                streetRepository.getStreetByAddressUuid(addressUuid)?.uuid
            }
        } else {
            postOrder.cafeUuid
        }
        cafeUuid ?: return null
        val insertOrder = InsertOrder(
            time = currentMillis,
            isDelivery = postOrder.isDelivery,
            code = generateCode(currentMillis),
            addressDescription = postOrder.addressDescription,
            comment = postOrder.comment,
            deferredTime = postOrder.deferredTime,
            status = OrderStatus.NOT_ACCEPTED.name,
            cafeUuid = cafeUuid.toUuid(),
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

    fun generateCode(currentMillis: Long): String {
        val currentSeconds = currentMillis / 1000

        val number = (currentSeconds % (CODE_LETTERS.length * CODE_NUMBER_COUNT)).toInt()
        val codeLetter = CODE_LETTERS[number % CODE_LETTERS.length].toString()
        val codeNumber = (number / CODE_LETTERS.length)
        val codeNumberString = if (codeNumber < 10) {
            "0$codeNumber"
        } else {
            codeNumber.toString()
        }

        return codeLetter + CODE_DIVIDER + codeNumberString
    }

    fun sendNotification(cafeOrder: GetCafeOrder?) {
        if (cafeOrder == null) {
            return
        }

        val messageId = firebaseMessaging.send(
            Message.builder()
                .putData(ORDER_KOD_KEY, cafeOrder.code)
                .setTopic(cafeOrder.cafeUuid)
                .build()
        )
        println("Message $messageId sent")
    }
}