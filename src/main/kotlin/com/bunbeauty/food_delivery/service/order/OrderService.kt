package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.Constants.CODE_DIVIDER
import com.bunbeauty.food_delivery.data.Constants.CODE_LETTERS
import com.bunbeauty.food_delivery.data.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.food_delivery.data.Constants.ORDER_HISTORY_DAY_COUNT
import com.bunbeauty.food_delivery.data.enums.OrderStatus
import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.order.*
import com.bunbeauty.food_delivery.data.repo.order.IOrderRepository
import com.bunbeauty.food_delivery.data.repo.street.IStreetRepository
import org.joda.time.DateTime

class OrderService(private val orderRepository: IOrderRepository, private val streetRepository: IStreetRepository) :
    IOrderService {

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
        return orderRepository.getOrderListByCafeUuid(cafeUuid.toUuid(), limitTime)
    }

    override suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder? {
        return orderRepository.updateOrderStatusByUuid(orderUuid.toUuid(), patchOrder.status)
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