package com.bunbeauty.food_delivery.service.statistic

import com.bunbeauty.food_delivery.data.model.statistic.GetStatistic

interface IStatisticService {

    suspend fun getStatisticList(userUuid: String, cafeUuid: String, period: String): List<GetStatistic>?
}