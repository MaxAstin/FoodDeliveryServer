package com.bunbeauty.fooddelivery.service.order

import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderUpdate
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.model.order.client.patch.PatchOrder
import com.bunbeauty.fooddelivery.data.model.order.client.post.PostOrder
import com.bunbeauty.fooddelivery.data.model.order.client.post.PostOrderV2
import kotlinx.coroutines.flow.Flow

interface IOrderService {

    suspend fun getOrderListByCafeUuid(cafeUuid: String): List<GetCafeOrder>
    suspend fun getOrderListByUserUuid(userUuid: String, count: Int?): List<GetClientOrder>
    suspend fun getOrderListByUserUuidV2(userUuid: String, count: Int?, orderUuid: String?): List<GetClientOrderV2>
    suspend fun getOrderByUuid(uuid: String): GetCafeOrderDetails?
    suspend fun getOrderByUuidV2(uuid: String): GetCafeOrderDetailsV2?
    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrder): GetClientOrder?
    suspend fun createOrder(clientUserUuid: String, postOrder: PostOrderV2): GetClientOrderV2?
    suspend fun changeOrder(orderUuid: String, patchOrder: PatchOrder): GetCafeOrder?
    suspend fun deleteOrder(orderUuid: String): GetCafeOrder?
    suspend fun observeClientOrderUpdates(clientUserUuid: String): Flow<GetClientOrder>
    suspend fun observeClientOrderUpdatesV2(clientUserUuid: String): Flow<GetClientOrderUpdate>
    suspend fun observeCafeOrderUpdates(cafeUuid: String): Flow<GetCafeOrder>
    fun clientDisconnect(clientUserUuid: String)
    fun userDisconnect(cafeUuid: String)
}