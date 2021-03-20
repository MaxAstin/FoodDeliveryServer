package com.bunbeauty.food_delivery.service.order

import com.bunbeauty.food_delivery.data.model.order.GetCafeOrder
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.PatchOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import kotlinx.coroutines.flow.Flow

interface IOrderService {

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder>
    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder?
    suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder?
    suspend fun observeActiveOrderList(clientUserUuid: String): Flow<List<GetClientOrder>>
}