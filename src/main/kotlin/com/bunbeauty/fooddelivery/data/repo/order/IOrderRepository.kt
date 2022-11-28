package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.model.order.*
import java.util.*

interface IOrderRepository {

    suspend fun insertOrder(insertOrder: InsertOrder): GetClientOrder
    suspend fun insertOrder(insertOrder: InsertOrderV2): GetClientOrderV2
    suspend fun getOrderListByCafeUuidLimited(cafeUuid: UUID, limitTime: Long): List<GetCafeOrder>
    suspend fun getOrderListByUserUuid(userUuid: UUID, count: Int?): List<GetClientOrder>
    suspend fun getOrderListByUserUuidV2(userUuid: UUID, count: Int?): List<GetClientOrderV2>
    suspend fun getOrderByUuid(orderUuid: UUID): GetCafeOrderDetails?
    suspend fun getOrderByUuidV2(orderUuid: UUID): GetCafeOrderDetailsV2?
    suspend fun getOrderListByCompanyUuidLimited(companyUuid: UUID, limitTime: Long): List<GetClientOrderV2>
    suspend fun getOrderListByCafeUuid(cafeUuid: UUID, startTimeMillis: Long, endTimeMillis: Long): List<GetCafeOrder>
    suspend fun getOrderDetailsListByCafeUuid(cafeUuid: UUID, startTimeMillis: Long, endTimeMillis: Long): List<GetCafeOrderDetailsV2>
    suspend fun getClientOrderByUuid(orderUuid: UUID): GetClientOrder?
    suspend fun getCafeOrderByUuid(orderUuid: UUID): GetCafeOrder?
    suspend fun deleteCafeOrderByUuid(orderUuid: UUID): GetCafeOrder?
    suspend fun updateOrderStatusByUuid(orderUuid: UUID, status: String): GetCafeOrder?
    suspend fun observeActiveOrderList(clientUserUuid: UUID): List<GetClientOrder>

}