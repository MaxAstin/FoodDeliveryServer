package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderUpdate
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.model.order.client.insert.InsertOrder
import com.bunbeauty.fooddelivery.data.model.order.client.insert.InsertOrderV2
import java.util.UUID

interface IOrderRepository {

    suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder
    suspend fun insertOrder(insertOrder: InsertOrderV2): GetClientOrderV2
    suspend fun getOrderListByCafeUuidLimited(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder>
    suspend fun getOrderListByUserUuid(userUuid: UUID, count: Int?): List<GetClientOrder>
    suspend fun getOrderListByUserUuidV2(userUuid: UUID, count: Int?): List<GetClientOrderV2>
    suspend fun getClientOrderByUuidV2(userUuid: UUID, orderUuid: UUID): GetClientOrderV2?
    suspend fun getOrderByUuid(orderUuid: UUID): GetCafeOrderDetails?
    suspend fun getOrderByUuidV2(orderUuid: UUID): GetCafeOrderDetailsV2?
    suspend fun getOrderListByCompanyUuidLimited(companyUuid: UUID, limitTime: Long): List<GetClientOrderV2>
    suspend fun getOrderDetailsListByCafeUuid(cafeUuid: UUID, startTimeMillis: Long, endTimeMillis: Long): List<GetCafeOrderDetailsV2>
    suspend fun getStatisticOrderListByCafeUuid(cafeUuid: UUID, startTimeMillis: Long, endTimeMillis: Long): List<GetStatisticOrder>
    suspend fun getClientOrderByUuid(orderUuid: UUID): GetClientOrder?
    suspend fun getClientOrderUpdateByUuid(orderUuid: UUID): GetClientOrderUpdate?
    suspend fun getCafeOrderByUuid(orderUuid: UUID): GetCafeOrder?
    suspend fun deleteCafeOrderByUuid(orderUuid: UUID): GetCafeOrder?
    suspend fun updateOrderStatusByUuid(orderUuid: UUID, status: String): GetCafeOrder?

}