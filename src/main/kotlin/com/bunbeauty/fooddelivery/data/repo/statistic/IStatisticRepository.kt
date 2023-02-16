package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticDay
import com.bunbeauty.fooddelivery.data.model.statistic.insert.InsertStatisticDay

interface IStatisticRepository {

    suspend fun insetStatisticDay(insertStatisticDay: InsertStatisticDay): GetStatisticDay

}