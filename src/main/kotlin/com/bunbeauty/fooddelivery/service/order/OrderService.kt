package com.bunbeauty.fooddelivery.service.order

import com.bunbeauty.fooddelivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.CODE_LETTERS
import com.bunbeauty.fooddelivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.fooddelivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.*
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.LinkedHashMap

class OrderService(private val orderRepository: IOrderRepository, private val streetRepository: IStreetRepository) :
    IOrderService {

    val listenerMap: LinkedHashMap<String, MutableSharedFlow<GetClientOrder>> = LinkedHashMap()

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
        return orderRepository.insertOrder(insertOrder)
    }

    override suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder> {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(ORDER_HISTORY_DAY_COUNT).millis
        return orderRepository.getOrderListByCafeUuidLimited(cafeUuid.toUuid(), limitTime)
    }

    override suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder? {
        val changedOrder = orderRepository.updateOrderStatusByUuid(orderUuid.toUuid(), patchOrder.status)

        val clientOrder = orderRepository.getOrderByUuid(orderUuid.toUuid())
        val clientUserUuid = changedOrder?.clientUser?.uuid
        if (clientOrder != null && clientUserUuid != null) {
            listenerMap[clientUserUuid]?.emit(clientOrder)
        }

        return changedOrder
    }

    override suspend fun observeChangedOrder(clientUserUuid: String): SharedFlow<GetClientOrder> {
        val mutableSharedFlow = MutableSharedFlow<GetClientOrder>(replay = 0)
        listenerMap[clientUserUuid] = mutableSharedFlow
        return mutableSharedFlow.asSharedFlow()
    }

    override fun clientDisconnect(clientUserUuid: String) {
        println("clientDisconnect before: listenerMap.size = ${listenerMap.size}")
        listenerMap.remove(clientUserUuid)
        println("clientDisconnect after: listenerMap.size = ${listenerMap.size}")
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
}