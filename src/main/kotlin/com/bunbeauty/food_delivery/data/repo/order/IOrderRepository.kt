package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.model.order.PostOrder
import org.jetbrains.exposed.sql.ResultRow

interface IOrderRepository {

    suspend fun insertOrder(postOrder: PostOrder): ResultRow?
}