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
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.bunbeauty.fooddelivery.service.date_time.IDateTimeService

class StatisticService(
    private val orderRepository: IOrderRepository,
    private val dateTimeService: IDateTimeService,
    private val userRepository: IUserRepository,
) : IStatisticService {

    override suspend fun getStatisticList(userUuid: String, cafeUuid: String, period: String): List<GetStatistic>? {
        val statisticPeriod = StatisticPeriod.values().find { statisticPeriod ->
            statisticPeriod.name == period
        } ?: return null

        return if (cafeUuid == ALL) {
            val companyUuid = userRepository.getCompanyUuidByUserUuid(userUuid.toUuid()) ?: return null
            getStatisticByCompanyUuid(companyUuid, statisticPeriod)
        } else {
            getStatisticByCafeUuid(cafeUuid, statisticPeriod)
        }
    }

    suspend fun getStatisticByCafeUuid(cafeUuid: String, statisticPeriod: StatisticPeriod): List<GetStatistic> {
        val orderList = orderRepository.getOrderListByCafeUuid(cafeUuid.toUuid())
        return mapToStatisticList(orderList, getTimestampConverter(statisticPeriod))
    }

    suspend fun getStatisticByCompanyUuid(companyUuid: String, statisticPeriod: StatisticPeriod): List<GetStatistic> {
        val orderList = orderRepository.getOrderListByCompanyUuid(companyUuid.toUuid())
        return mapToStatisticList(orderList, getTimestampConverter(statisticPeriod))
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
            order.status == OrderStatus.DELIVERED.name || order.status == OrderStatus.DONE.name
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