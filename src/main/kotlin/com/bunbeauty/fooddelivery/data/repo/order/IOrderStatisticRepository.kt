package com.bunbeauty.fooddelivery.data.repo.order

import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticOrder
import java.util.*

interface IOrderStatisticRepository {

    suspend fun getOrderListByCafeUuid(
        cafeUuid: UUID,
        fromTime: Long,
        toTime: Long,
    ): List<GetStatisticOrder>

    suspend fun getOrderListByCompanyUuid(
        companyUuid: UUID,
        fromTime: Long,
        toTime: Long,
    ): List<GetStatisticOrder>

}