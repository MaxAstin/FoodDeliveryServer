package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.statistic.StatisticService
import com.bunbeauty.fooddelivery.routing.extension.getWithListResult
import com.bunbeauty.fooddelivery.routing.extension.manager
import com.bunbeauty.fooddelivery.routing.extension.respondNotFound
import com.bunbeauty.fooddelivery.routing.extension.respondOkOrBad
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStatisticRouting() {

    routing {
        getLastMonthCompanyStatistic()
        authenticate {
            getStatistic()
            getStatisticDetails()
        }
    }
}

private fun Route.getStatistic() {

    val statisticService: StatisticService by inject()

    get("/statistic") {
        manager { request ->
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER]
            val period = call.parameters[PERIOD_PARAMETER] ?: error("$PERIOD_PARAMETER is required")
            val statisticList = statisticService.getStatisticList(
                userUuid = request.jwtUser.uuid,
                cafeUuid = cafeUuid,
                period = period
            )
            call.respondOkOrBad(statisticList)
        }
    }
}

private fun Route.getStatisticDetails() {

    get("/statistic/details") {
        manager {
            call.respondNotFound()
        }
    }
}

private fun Route.getLastMonthCompanyStatistic() {

    val statisticService: StatisticService by inject()

    get("/statistic/last") {
        getWithListResult{
            statisticService.getLastMonthCompanyStatistic()
        }
    }
}
