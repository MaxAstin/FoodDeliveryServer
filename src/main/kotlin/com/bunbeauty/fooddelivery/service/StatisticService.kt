package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertCafeStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertCompanyStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICafeStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class StatisticService(
    private val orderStatisticRepository: IOrderStatisticRepository,
    private val companyRepository: ICompanyRepository,
    private val cafeRepository: ICafeRepository,
    private val companyStatisticRepository: ICompanyStatisticRepository,
    private val cafeStatisticRepository: ICafeStatisticRepository,
    private val userRepository: IUserRepository,
) {

    suspend fun getStatisticList(userUuid: String, cafeUuid: String?, period: String): List<GetStatistic>? {
        val periodType = PeriodType.valueOf(period)
        val user = userRepository.getUserByUuid(userUuid.toUuid()) ?: return null
        val currentDateTime = getTodayDateTime(user.company.offset)
        val startTimeMillis = currentDateTime.minusMonths(24)
            .minusDays(currentDateTime.dayOfMonth - 1)
            .withTimeAtStartOfDay()
            .millis

        return if (cafeUuid == null) {
            companyStatisticRepository.getStatisticListByTimePeriodTypeCompany(
                time = startTimeMillis,
                periodType = periodType,
                companyUuid = user.company.uuid.toUuid(),
            )
        } else {
            cafeStatisticRepository.getStatisticListByTimePeriodTypeCafe(
                time = startTimeMillis,
                periodType = periodType,
                cafeUuid = cafeUuid.toUuid(),
            )
        }
    }

    suspend fun updateStatistic() {
        companyRepository.getCompanyList().forEach { company ->
            val toDateTime = getTodayDateTime(company.offset)
            val dayPeriodFromDateTime = getDayPeriodFromDateTime(toDateTime)
            val weekPeriodFromDateTime = getWeekPeriodFromDateTime(toDateTime)
            val monthPeriodFromDateTime = getMonthPeriodFromDateTime(toDateTime)

            updateCompanyStatistic(
                company = company,
                periodType = PeriodType.DAY,
                fromDateTime = dayPeriodFromDateTime,
                toDateTime = toDateTime,
            )
            updateCompanyStatistic(
                company = company,
                periodType = PeriodType.WEEK,
                fromDateTime = weekPeriodFromDateTime,
                toDateTime = toDateTime,
            )
            updateCompanyStatistic(
                company = company,
                periodType = PeriodType.MONTH,
                fromDateTime = monthPeriodFromDateTime,
                toDateTime = toDateTime,
            )
            cafeRepository.getCafeListByCompanyUuid(company.uuid.toUuid()).forEach { cafe ->
                updateCafeStatistic(
                    cafe = cafe,
                    periodType = PeriodType.DAY,
                    fromDateTime = dayPeriodFromDateTime,
                    toDateTime = toDateTime,
                )
                updateCafeStatistic(
                    cafe = cafe,
                    periodType = PeriodType.WEEK,
                    fromDateTime = weekPeriodFromDateTime,
                    toDateTime = toDateTime,
                )
                updateCafeStatistic(
                    cafe = cafe,
                    periodType = PeriodType.MONTH,
                    fromDateTime = monthPeriodFromDateTime,
                    toDateTime = toDateTime,
                )
            }
        }
    }

    private suspend inline fun updateCompanyStatistic(
        company: GetCompany,
        periodType: PeriodType,
        fromDateTime: DateTime,
        toDateTime: DateTime,
    ) {
        val statisticOrderList = orderStatisticRepository.getOrderListByCompanyUuid(
            companyUuid = company.uuid.toUuid(),
            fromTime = fromDateTime.millis,
            toTime = toDateTime.millis,
        )

        val getStatistic = companyStatisticRepository.getStatisticByTimePeriodTypeCompany(
            time = fromDateTime.millis,
            periodType = periodType,
            companyUuid = company.uuid.toUuid(),
        )
        if (getStatistic == null) {
            val insertCompanyStatisticDay = InsertCompanyStatistic(
                time = fromDateTime.millis,
                periodType = periodType,
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList),
                companyUuid = company.uuid.toUuid(),
            )
            companyStatisticRepository.insetStatistic(insertCompanyStatisticDay)
        } else {
            val updateStatistic = UpdateStatistic(
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList)
            )
            companyStatisticRepository.updateStatistic(getStatistic.uuid.toUuid(), updateStatistic)
        }
    }

    private suspend inline fun updateCafeStatistic(
        cafe: GetCafe,
        periodType: PeriodType,
        fromDateTime: DateTime,
        toDateTime: DateTime,
    ) {
        val statisticOrderList = orderStatisticRepository.getOrderListByCafeUuid(
            cafeUuid = cafe.uuid.toUuid(),
            fromTime = fromDateTime.millis,
            toTime = toDateTime.millis,
        )

        val getStatistic = cafeStatisticRepository.getStatisticByTimePeriodTypeCafe(
            time = fromDateTime.millis,
            periodType = periodType,
            cafeUuid = cafe.uuid.toUuid(),
        )
        if (getStatistic == null) {
            val insertCompanyStatisticDay = InsertCafeStatistic(
                time = fromDateTime.millis,
                periodType = periodType,
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList),
                cafeUuid = cafe.uuid.toUuid(),
            )
            cafeStatisticRepository.insetStatistic(insertCompanyStatisticDay)
        } else {
            val updateStatistic = UpdateStatistic(
                orderCount = statisticOrderList.size,
                orderProceeds = calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList)
            )
            cafeStatisticRepository.updateStatistic(getStatistic.uuid.toUuid(), updateStatistic)
        }
    }

    private fun getTodayDateTime(offset: Int): DateTime {
        return DateTime.now()
            .withZone(DateTimeZone.forOffsetHours(offset))
            .withTimeAtStartOfDay()
    }

    private fun getDayPeriodFromDateTime(toDateTime: DateTime): DateTime {
        return toDateTime.minusDays(1)
    }

    private fun getWeekPeriodFromDateTime(toDateTime: DateTime): DateTime {
        return toDateTime.run {
            if (dayOfWeek == 1) {
                minusDays(7)
            } else {
                minusDays(dayOfWeek - 1)
            }
        }
    }

    private fun getMonthPeriodFromDateTime(toDateTime: DateTime): DateTime {
        return toDateTime.run {
            if (dayOfMonth == 1) {
                minusMonths(1)
            } else {
                minusDays(dayOfMonth - 1)
            }
        }
    }

    private fun calculateOrderProceeds(statisticOrderList: List<GetStatisticOrder>): Int {
        return statisticOrderList.sumOf { statisticOrder ->
            statisticOrder.statisticOrderProductList.sumOf { statisticOrderProduct ->
                statisticOrderProduct.newPrice * statisticOrderProduct.count
            }
        }
    }

    private fun calculateStatisticProductList(statisticOrderList: List<GetStatisticOrder>): List<InsertStatisticProduct> {
        return statisticOrderList.flatMap { statisticOrder ->
            statisticOrder.statisticOrderProductList
        }.groupBy { statisticOrderProduct ->
            statisticOrderProduct.menuProductUuid
        }.map { (_, statisticOrderProductList) ->
            InsertStatisticProduct(
                name = statisticOrderProductList.first().name,
                photoLink = statisticOrderProductList.first().photoLink,
                productCount = statisticOrderProductList.sumOf { statisticOrderProduct ->
                    statisticOrderProduct.count
                },
                proceeds = statisticOrderProductList.sumOf { statisticOrderProduct ->
                    statisticOrderProduct.newPrice * statisticOrderProduct.count
                }
            )
        }
    }

}