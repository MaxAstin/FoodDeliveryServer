package com.bunbeauty.fooddelivery.service.statistic

import com.bunbeauty.fooddelivery.data.model.statistic.GetStatistic

interface IStatisticService {

    suspend fun getStatisticList(userUuid: String, cafeUuid: String, period: String): List<GetStatistic>?
}