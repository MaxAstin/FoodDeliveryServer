package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.mapper.IOrderMapper
import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.data.repo.order.IOrderRepository

class OrderService(private val orderRepository: IOrderRepository, private val orderMapper: IOrderMapper) :
    IOrderService {


    override suspend fun createOrder(postOrder: PostOrder): GetOrder? {
        return orderRepository.insertOrder(postOrder)?.let { resultRow ->
            orderMapper.rowToModel(resultRow)
        }
    }
}