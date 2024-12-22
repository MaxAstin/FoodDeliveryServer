package com.bunbeauty.fooddelivery.domain.feature.statistic

import com.bunbeauty.fooddelivery.data.features.statistic.CompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.LastMonthCompanyStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.domain.toUuid
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class GetLastMonthCompanyStatisticUseCase(
    private val companyRepository: CompanyRepository,
    private val companyStatisticRepository: CompanyStatisticRepository,
) {

    suspend operator fun invoke(): List<LastMonthCompanyStatistic> {
        return companyRepository.getCompanyList().map { company ->
            getCompanyStatistic(company = company)
        }
    }

    private suspend fun getCompanyStatistic(company: Company): LastMonthCompanyStatistic {
        val todayDateTime = DateTime.now()
            .withZone(DateTimeZone.forOffsetHours(company.offset))
            .withTimeAtStartOfDay()
        val statisticDateTime = if (todayDateTime.dayOfMonth <= 15) {
            todayDateTime.minusMonths(1)
                .minusDays(todayDateTime.dayOfMonth - 1)
        } else {
            todayDateTime.minusDays(todayDateTime.dayOfMonth - 1)
        }
        val month = if (statisticDateTime.monthOfYear < 10) {
            "0${statisticDateTime.monthOfYear}"
        } else {
            statisticDateTime.monthOfYear.toString()
        }
        val companyStatistic = companyStatisticRepository.getStatisticByTimePeriodTypeCompany(
            time = statisticDateTime.millis,
            periodType = PeriodType.MONTH,
            companyUuid = company.uuid.toUuid(),
        )

        return LastMonthCompanyStatistic(
            period = "${statisticDateTime.dayOfMonth}/$month",
            companyName = company.name,
            orderProceeds = companyStatistic?.orderProceeds
        )
    }

}