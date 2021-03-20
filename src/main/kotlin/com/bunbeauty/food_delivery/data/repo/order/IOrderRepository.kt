package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.model.order.GetCafeOrder
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.InsertOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import java.util.*

interface IOrderRepository {

    suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder
    suspend fun getOrderListByCafeUuid(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder>
    suspend fun updateOrderStatusByUuid(orderUuid: UUID, status:String): GetCafeOrder?
    suspend fun observeActiveOrderList(clientUserUuid: UUID): List<GetClientOrder>

}