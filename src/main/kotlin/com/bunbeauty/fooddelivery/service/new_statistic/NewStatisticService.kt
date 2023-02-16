package com.bunbeauty.fooddelivery.service.new_statistic

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticOrder
import com.bunbeauty.fooddelivery.data.model.statistic.insert.InsertStatisticDay
import com.bunbeauty.fooddelivery.data.model.statistic.insert.InsertStatisticProduct
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderStatisticRepository
import com.bunbeauty.fooddelivery.data.repo.statistic.IStatisticRepository
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class NewStatisticService(
    private val orderStatisticRepository: IOrderStatisticRepository,
    private val companyRepository: ICompanyRepository,
    private val statisticRepository: IStatisticRepository,
) {

    suspend fun updateStatistic() {
        companyRepository.getCompanyList().forEach { company ->
            updateLastDayStatistic(company)
        }

        //TODO
        //orderStatisticRepository.getOrderListByCompanyUuid()
    }

    private suspend fun updateLastDayStatistic(company: GetCompany) {
        val toTime = DateTime.now()
            .withZone(DateTimeZone.forOffsetHours(company.offset))
            .withTimeAtStartOfDay()
        val fromTime = toTime.minusDays(1)
        val statisticOrderList = orderStatisticRepository.getOrderListByCompanyUuid(
            companyUuid = company.uuid.toUuid(),
            fromTime = fromTime.millis,
            toTime = toTime.millis,
        )
        val insertStatisticDay = toInsertStatisticDay(fromTime.millis, statisticOrderList, company)
        statisticRepository.insetStatisticDay(insertStatisticDay)
    }

    private fun toInsertStatisticDay(
        time: Long,
        statisticOrderList: List<GetStatisticOrder>,
        company: GetCompany,
    ): InsertStatisticDay {
        return InsertStatisticDay(
            time = time,
            orderCount = statisticOrderList.size,
            proceeds = statisticOrderList.sumOf { statisticOrder ->
                statisticOrder.statisticOrderProductList.sumOf { statisticOrderProduct ->
                    statisticOrderProduct.newPrice * statisticOrderProduct.count
                }
            },
            statisticProductList = statisticOrderList.flatMap { statisticOrder ->
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
            },
            companyUuid = company.uuid.toUuid()
        )
    }


}