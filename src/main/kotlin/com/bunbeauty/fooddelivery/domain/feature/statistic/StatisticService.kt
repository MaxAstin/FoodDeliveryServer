package com.bunbeauty.fooddelivery.domain.feature.statistic

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.statistic.CompanyStatisticRepository
import com.bunbeauty.fooddelivery.data.features.statistic.OrderStatisticRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.ICafeStatisticRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateCostWithDiscountUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductTotalUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CalculateOrderProductsNewCostUseCase
import com.bunbeauty.fooddelivery.domain.feature.statistic.mapper.toGetLastMonthCompanyStatistic
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.GetLastMonthCompanyStatistic
import com.bunbeauty.fooddelivery.domain.feature.statistic.model.OrderProductWithDiscount
import com.bunbeauty.fooddelivery.domain.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.domain.model.new_statistic.UpdateStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertCafeStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertCompanyStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.domain.toUuid
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class StatisticService(
    private val orderStatisticRepository: OrderStatisticRepository,
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase,
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase,
    private val calculateCostWithDiscountUseCase: CalculateCostWithDiscountUseCase,
    private val companyRepository: CompanyRepository,
    private val cafeRepository: CafeRepository,
    private val companyStatisticRepository: CompanyStatisticRepository,
    private val cafeStatisticRepository: ICafeStatisticRepository,
    private val userRepository: UserRepository,
    private val getLastMonthCompanyStatisticUseCase: GetLastMonthCompanyStatisticUseCase
) {

    suspend fun getStatisticList(userUuid: String, cafeUuid: String?, period: String): List<GetStatistic> {
        val periodType = PeriodType.valueOf(period)
        val user = userRepository.getUserByUuid(userUuid.toUuid())
            .orThrowNotFoundByUuidError(userUuid)
        val currentDateTime = getTodayDateTime(user.company.offset)
        val startTimeMillis = currentDateTime.minusMonths(24)
            .minusDays(currentDateTime.dayOfMonth - 1)
            .withTimeAtStartOfDay()
            .millis

        return if (cafeUuid == null) {
            companyStatisticRepository.getStatisticListByTimePeriodTypeCompany(
                time = startTimeMillis,
                periodType = periodType,
                companyUuid = user.companyUuid.toUuid()
            )
        } else {
            cafeStatisticRepository.getStatisticListByTimePeriodTypeCafe(
                time = startTimeMillis,
                periodType = periodType,
                cafeUuid = cafeUuid.toUuid()
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
                toDateTime = toDateTime
            )
            updateCompanyStatistic(
                company = company,
                periodType = PeriodType.WEEK,
                fromDateTime = weekPeriodFromDateTime,
                toDateTime = toDateTime
            )
            updateCompanyStatistic(
                company = company,
                periodType = PeriodType.MONTH,
                fromDateTime = monthPeriodFromDateTime,
                toDateTime = toDateTime
            )
            cafeRepository.getCafeListByCompanyUuid(companyUuid = company.uuid)
                .forEach { cafe ->
                    updateCafeStatistic(
                        cafe = cafe,
                        periodType = PeriodType.DAY,
                        fromDateTime = dayPeriodFromDateTime,
                        toDateTime = toDateTime
                    )
                    updateCafeStatistic(
                        cafe = cafe,
                        periodType = PeriodType.WEEK,
                        fromDateTime = weekPeriodFromDateTime,
                        toDateTime = toDateTime
                    )
                    updateCafeStatistic(
                        cafe = cafe,
                        periodType = PeriodType.MONTH,
                        fromDateTime = monthPeriodFromDateTime,
                        toDateTime = toDateTime
                    )
                }
        }
    }

    suspend fun getLastMonthCompanyStatistic(): List<GetLastMonthCompanyStatistic> {
        return getLastMonthCompanyStatisticUseCase().map(toGetLastMonthCompanyStatistic)
    }

    private suspend inline fun updateCompanyStatistic(
        company: Company,
        periodType: PeriodType,
        fromDateTime: DateTime,
        toDateTime: DateTime
    ) {
        val orderList = orderStatisticRepository.getOrderListByCompanyUuid(
            companyUuid = company.uuid.toUuid(),
            fromTime = fromDateTime.millis,
            toTime = toDateTime.millis
        )

        val getStatistic = companyStatisticRepository.getStatisticByTimePeriodTypeCompany(
            time = fromDateTime.millis,
            periodType = periodType,
            companyUuid = company.uuid.toUuid()
        )
        if (getStatistic == null) {
            val insertCompanyStatisticDay = InsertCompanyStatistic(
                time = fromDateTime.millis,
                periodType = periodType,
                orderCount = orderList.size,
                orderProceeds = calculateOrderProceeds(orderList),
                statisticProductList = calculateStatisticProductList(orderList),
                companyUuid = company.uuid.toUuid()
            )
            companyStatisticRepository.insetStatistic(insertCompanyStatisticDay)
        } else {
            val updateStatistic = UpdateStatistic(
                orderCount = orderList.size,
                orderProceeds = calculateOrderProceeds(orderList),
                statisticProductList = calculateStatisticProductList(orderList)
            )
            companyStatisticRepository.updateStatistic(getStatistic.uuid.toUuid(), updateStatistic)
        }
    }

    private suspend inline fun updateCafeStatistic(
        cafe: Cafe,
        periodType: PeriodType,
        fromDateTime: DateTime,
        toDateTime: DateTime
    ) {
        val statisticOrderList = orderStatisticRepository.getOrderListByCafeUuid(
            cafeUuid = cafe.uuid.toUuid(),
            fromTime = fromDateTime.millis,
            toTime = toDateTime.millis
        )

        val getStatistic = cafeStatisticRepository.getStatisticByTimePeriodTypeCafe(
            time = fromDateTime.millis,
            periodType = periodType,
            cafeUuid = cafe.uuid.toUuid()
        )
        if (getStatistic == null) {
            val insertCompanyStatisticDay = InsertCafeStatistic(
                time = fromDateTime.millis,
                periodType = periodType,
                orderCount = statisticOrderList.size,
                orderProceeds =
                calculateOrderProceeds(statisticOrderList),
                statisticProductList = calculateStatisticProductList(statisticOrderList),
                cafeUuid = cafe.uuid.toUuid()
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

    private fun calculateOrderProceeds(orderList: List<Order>): Int {
        return orderList.sumOf { order ->
            calculateOrderProductsNewCostUseCase(
                orderProductList = order.orderProducts,
                percentDiscount = order.percentDiscount
            )
        }
    }

    private fun calculateStatisticProductList(orderList: List<Order>): List<InsertStatisticProduct> {
        return orderList.flatMap { order ->
            order.orderProducts.map { orderProduct ->
                OrderProductWithDiscount(
                    orderProduct = orderProduct,
                    percentDiscount = order.percentDiscount
                )
            }
        }.groupBy { orderProductWithDiscount ->
            orderProductWithDiscount.orderProduct.menuProduct.uuid
        }.mapNotNull { (_, orderProductWithDiscountList) ->
            val firstOrderProduct = orderProductWithDiscountList.firstOrNull()?.orderProduct ?: return@mapNotNull null
            InsertStatisticProduct(
                name = firstOrderProduct.name,
                photoLink = firstOrderProduct.photoLink,
                productCount = orderProductWithDiscountList.sumOf { orderProductWithDiscount ->
                    orderProductWithDiscount.orderProduct.count
                },
                proceeds = orderProductWithDiscountList.sumOf { orderProductWithDiscount ->
                    val productTotal =
                        calculateOrderProductTotalUseCase(orderProduct = orderProductWithDiscount.orderProduct)
                    val orderProductCostWithDiscount = calculateCostWithDiscountUseCase(
                        cost = productTotal.newTotalCost,
                        percentDiscount = orderProductWithDiscount.percentDiscount
                    )

                    orderProductCostWithDiscount
                }
            )
        }
    }
}
