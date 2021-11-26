package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder

interface IOrderRepository {

    suspend fun insertOrder(postOrder: PostOrder): GetOrder

}