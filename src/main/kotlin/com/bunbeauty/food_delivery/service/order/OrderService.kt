package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.data.repo.order.IOrderRepository

class OrderService(private val orderRepository: IOrderRepository) :
    IOrderService {

    override suspend fun createOrder(postOrder: PostOrder): GetOrder = orderRepository.insertOrder(postOrder)
}