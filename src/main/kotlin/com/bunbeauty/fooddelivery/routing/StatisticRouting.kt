package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.START_TIME_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.manager
import com.bunbeauty.fooddelivery.routing.extension.respondBad
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.service.statistic.StatisticService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStatisticRouting() {

    routing {
        authenticate {
            getStatistic()
        }
    }
}

fun Route.getStatistic() {

    val statisticService: StatisticService by inject()

    get("/statistic") {
        manager { request ->
            println("GET /statistic start")
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER]
            val period = call.parameters[PERIOD_PARAMETER] ?: error("$PERIOD_PARAMETER is required")
            val statisticList = statisticService.getStatisticList(request.jwtUser.uuid, cafeUuid, period)
            println("GET /statistic end")
            if (statisticList == null) {
                call.respondBad("Wrong parameters values")
            } else {
                call.respondOk(statisticList)
            }
        }
    }

    get("/statistic/details") {
        manager { request ->
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER]
            val period = call.parameters[PERIOD_PARAMETER] ?: error("$PERIOD_PARAMETER is required")
            val startTime =
                call.parameters[START_TIME_PARAMETER]?.toLong() ?: error("$START_TIME_PARAMETER is required")
            val statisticList = statisticService.getStatisticDetails(request.jwtUser.uuid, cafeUuid, period, startTime)
            if (statisticList == null) {
                call.respondBad("Wrong parameters values")
            } else {
                call.respondOk(statisticList)
            }
        }
    }
}
