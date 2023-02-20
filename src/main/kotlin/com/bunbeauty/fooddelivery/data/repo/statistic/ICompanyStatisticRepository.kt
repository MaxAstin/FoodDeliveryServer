package com.bunbeauty.fooddelivery.data.repo.statistic

import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertCompanyStatistic
import java.util.*

interface ICompanyStatisticRepository {

    suspend fun getStatisticByTimePeriodTypeCompany(
        periodType: PeriodType,
        time: Long,
        companyUuid: UUID,
    ): GetStatistic?

    suspend fun insetStatistic(insertCompanyStatistic: InsertCompanyStatistic): GetStatistic

    suspend fun updateStatistic(statisticUuid: UUID, updateStatistic: UpdateStatistic): GetStatistic?

}