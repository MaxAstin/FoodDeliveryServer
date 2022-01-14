package com.bunbeauty.fooddelivery.service.order

import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.PatchOrder
import com.bunbeauty.fooddelivery.data.model.order.PostOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface IOrderService {

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder>
    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder?
    suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder?
    suspend fun deleteOrder(orderUuid: String): GetCafeOrder?
    suspend fun observeClientOrderUpdates(clientUserUuid: String): SharedFlow<GetClientOrder>
    suspend fun observeCafeOrderUpdates(userUuid: String): SharedFlow<GetCafeOrder>
    fun clientDisconnect(clientUserUuid: String)
    fun userDisconnect(userUuid: String)
}