package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder

interface IOrderService {

    suspend fun createOrder(postOrder: PostOrder): GetOrder
}