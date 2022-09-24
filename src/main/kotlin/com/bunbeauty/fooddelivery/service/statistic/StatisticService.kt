package com.bunbeauty.fooddelivery.service.statistic

import com.bunbeauty.fooddelivery.data.Constants.ALL
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.enums.StatisticPeriod
import com.bunbeauty.fooddelivery.data.enums.StatisticPeriod.*
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct
import com.bunbeauty.fooddelivery.data.model.statistic.GetProductStatistic
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatistic
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
) : IStatisticService {

    override suspend fun getStatisticList(userUuid: String, cafeUuid: String, period: String): List<GetStatistic>? {
        val statisticPeriod = StatisticPeriod.values().find { statisticPeriod ->
            statisticPeriod.name == period
        } ?: return null
        val currentDateTime = DateTime.now()
        val limitTimeMillis = currentDateTime.minusMonths(3)
            .minusDays(currentDateTime.dayOfMonth - 1)
            .withTimeAtStartOfDay()
            .millis

        val cityUuid = userRepository.getUserByUuid(userUuid.toUuid())?.city?.uuid ?: return null
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid.toUuid())
        val getCafeOrderList: List<GetCafeOrder> = if (cafeUuid == ALL) {
            coroutineScope {
                cafeList.map { cafe ->
                    async {
                        orderRepository.getOrderListByCafeUuid(cafe.uuid.toUuid(), limitTimeMillis)
                    }
                }.awaitAll().flatten()
            }
        } else {
            val selectedCafe = cafeList.find { getCafe ->
                getCafe.uuid == cafeUuid
            } ?: return null
            orderRepository.getOrderListByCafeUuid(selectedCafe.uuid.toUuid(), limitTimeMillis)
        }

        return mapToStatisticList(getCafeOrderList, getTimestampConverter(statisticPeriod))
    }

    fun getTimestampConverter(statisticPeriod: StatisticPeriod): (Long) -> String {
        return when (statisticPeriod) {
            DAY -> dateTimeService::getDateDDMMMMYYYY
            WEEK -> dateTimeService::getWeekPeriod
            MONTH -> dateTimeService::getDateMMMMYYYY
        }
    }

    inline fun mapToStatisticList(
        orderList: List<GetCafeOrder>,
        timestampConverter: (Long) -> String,
    ): List<GetStatistic> {
        return orderList.filter { order ->
            order.status == OrderStatus.DELIVERED.name
        }.groupBy { order ->
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

    fun getStartPeriodTime(orderList: List<GetCafeOrder>): Long {
        return orderList.minOf { getCafeOrder ->
            getCafeOrder.time
        }
    }

    fun countCost(orderList: List<GetCafeOrder>): Int {
        return orderList.sumOf { getCafeOrder ->
            countProductListCost(getCafeOrder.oderProductList)
        }
    }

    fun countAverageCheck(orderList: List<GetCafeOrder>): Int {
        return countCost(orderList) / orderList.size
    }

    fun getProductStatisticList(orderList: List<GetCafeOrder>): List<GetProductStatistic> {
        return orderList.flatMap { getCafeOrder ->
            getCafeOrder.oderProductList
        }.groupBy { getCafeOrder ->
            getCafeOrder.menuProduct.uuid
        }.map { entry ->
            GetProductStatistic(
                name = entry.value.first().menuProduct.name,
                orderCount = entry.value.size,
                productCount = countProduct(entry.value),
                proceeds = countProductListCost(entry.value),
            )
        }
    }

    fun countProduct(oderProductList: List<GetOrderProduct>): Int {
        return oderProductList.sumOf { getOrderProduct ->
            getOrderProduct.count
        }
    }

    fun countProductListCost(oderProductList: List<GetOrderProduct>): Int {
        return oderProductList.sumOf { getOrderProduct ->
            getOrderProduct.count * getOrderProduct.newPrice
        }
    }
}