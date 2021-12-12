package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.model.order.GetCafeOrder
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder

interface IOrderService {

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder>
    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder?
}