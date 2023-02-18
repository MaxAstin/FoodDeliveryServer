package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatistic
import java.util.*

interface IStatisticRepository {

    suspend fun getStatisticByTimePeriodTypeCompany(
        periodType: PeriodType,
        time: Long,
        companyUuid: UUID,
    ): GetStatistic?

    suspend fun insetStatistic(insertStatistic: InsertStatistic): GetStatistic

    suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic?

}