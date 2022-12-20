package com.bunbeauty.fooddelivery.service.statistic

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.enums.StatisticPeriod
import com.bunbeauty.fooddelivery.data.enums.StatisticPeriod.*
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct
import com.bunbeauty.fooddelivery.data.model.statistic.GetProductStatistic
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticDetails
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.bunbeauty.fooddelivery.service.date_time.IDateTimeService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.joda.time.DateTime

class StatisticService(
    private val orderRepository: IOrderRepository,
    private val dateTimeService: IDateTimeService,
    private val userRepository: IUserRepository,
    private val cafeRepository: ICafeRepository,
) {

    suspend fun getStatisticList(userUuid: String, cafeUuid: String?, period: String): List<GetStatistic>? {
        val statisticPeriod = StatisticPeriod.values().find { statisticPeriod ->
            statisticPeriod.name == period
        } ?: return null
        val currentDateTime = DateTime.now()
        val startTimeMillis = currentDateTime.minusMonths(3)
            .minusDays(currentDateTime.dayOfMonth - 1)
            .withTimeAtStartOfDay()
            .millis
        val endTimeMillis = currentDateTime.millis

        val cafeOrderList = getCafeOrderList(
            userUuid = userUuid,
            cafeUuid = cafeUuid,
            startTimeMillis = startTimeMillis,
            endTimeMillis = endTimeMillis,
        ) ?: return null

        return mapToStatisticList(cafeOrderList, getTimestampConverter(statisticPeriod))
    }

    suspend fun getStatisticDetails(
        userUuid: String,
        cafeUuid: String?,
        period: String,
        startTimeMillis: Long,
    ): GetStatisticDetails? {
        val statisticPeriod = StatisticPeriod.values().find { statisticPeriod ->
            statisticPeriod.name == period
        } ?: return null
        val startDateTime = DateTime(startTimeMillis)
        val endTimeMillis = when (statisticPeriod) {
            DAY -> startDateTime.plusDays(1)
            WEEK -> startDateTime.plusWeeks(1)
            MONTH -> startDateTime.plusMonths(1)
        }.millis

        val cafeOrderList = getCafeOrderList(
            userUuid = userUuid,
            cafeUuid = cafeUuid,
            startTimeMillis = startTimeMillis,
            endTimeMillis = endTimeMillis,
        ) ?: return null

        val timestampConverter = getTimestampConverter(statisticPeriod)
        return GetStatisticDetails(
            period = timestampConverter(startTimeMillis),
            orderCount = cafeOrderList.size,
            proceeds = countCost(cafeOrderList),
            averageCheck = countAverageCheck(cafeOrderList),
            productStatisticList = getProductStatisticList(cafeOrderList)
        )
    }

    private suspend fun getCafeOrderList(
        userUuid: String,
        cafeUuid: String?,
        startTimeMillis: Long,
        endTimeMillis: Long,
    ): List<GetCafeOrderDetailsV2>? {
        val cityUuid = userRepository.getUserByUuid(userUuid.toUuid())?.city?.uuid ?: return null
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid.toUuid())
        return if (cafeUuid == null) {
            coroutineScope {
                cafeList.map { cafe ->
                    async {
                        orderRepository.getOrderDetailsListByCafeUuid(cafe.uuid.toUuid(), startTimeMillis, endTimeMillis)
                    }
                }.awaitAll().flatten()
            }
        } else {
            val selectedCafe = cafeList.find { getCafe ->
                getCafe.uuid == cafeUuid
            } ?: return null
            orderRepository.getOrderDetailsListByCafeUuid(selectedCafe.uuid.toUuid(), startTimeMillis, endTimeMillis)
        }.filter { order ->
            order.status == OrderStatus.DELIVERED.name
        }
    }

    private fun getTimestampConverter(statisticPeriod: StatisticPeriod): (Long) -> String {
        return when (statisticPeriod) {
            DAY -> dateTimeService::getDateDDMMMMYYYY
            WEEK -> dateTimeService::getWeekPeriod
            MONTH -> dateTimeService::getDateMMMMYYYY
        }
    }

    private inline fun mapToStatisticList(
        orderList: List<GetCafeOrderDetailsV2>,
        timestampConverter: (Long) -> String,
    ): List<GetStatistic> {
        return orderList.groupBy { order ->
            timestampConverter(order.time)
        }.map { orderEntry ->
            GetStatistic(
                period = orderEntry.key,
                startPeriodTime = getStartPeriodTime(orderEntry.value),
                orderCount = orderEntry.value.size,
                proceeds = countCost(orderEntry.value),
                averageCheck = countAverageCheck(orderEntry.value),
                productStatisticList = getProductStatisticList(orderEntry.value)
            )
        }.sortedByDescending { getStatistic ->
            getStatistic.startPeriodTime
        }
    }

    private fun getStartPeriodTime(orderList: List<GetCafeOrderDetailsV2>): Long {
        return orderList.minOf { getCafeOrder ->
            getCafeOrder.time
        }
    }

    private fun countCost(orderList: List<GetCafeOrderDetailsV2>): Int {
        return orderList.sumOf { getCafeOrder ->
            countProductListCost(getCafeOrder.oderProductList)
        }
    }

    private fun countAverageCheck(orderList: List<GetCafeOrderDetailsV2>): Int {
        return countCost(orderList) / orderList.size
    }

    private fun getProductStatisticList(orderList: List<GetCafeOrderDetailsV2>): List<GetProductStatistic> {
        return orderList.flatMap { getCafeOrder ->
            getCafeOrder.oderProductList
        }.groupBy { getCafeOrder ->
            getCafeOrder.menuProduct.uuid
        }.map { (_, oderProductList) ->
            GetProductStatistic(
                name = oderProductList.first().menuProduct.name,
                orderCount = oderProductList.size,
                productCount = countProduct(oderProductList),
                proceeds = countProductListCost(oderProductList),
            )
        }.sortedBy { productStatistic ->
            productStatistic.proceeds
        }
    }

    private fun countProduct(oderProductList: List<GetOrderProduct>): Int {
        return oderProductList.sumOf { getOrderProduct ->
            getOrderProduct.count
        }
    }

    private fun countProductListCost(oderProductList: List<GetOrderProduct>): Int {
        return oderProductList.sumOf { getOrderProduct ->
            getOrderProduct.count * getOrderProduct.newPrice
        }
    }
}