package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertCafeStatistic
import java.util.*

interface ICafeStatisticRepository {

    suspend fun getStatisticByTimePeriodTypeCafe(
        periodType: PeriodType,
        time: Long,
        cafeUuid: UUID,
    ): GetStatistic?

    suspend fun insetStatistic(insertCafeStatistic: InsertCafeStatistic): GetStatistic

    suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic?

}