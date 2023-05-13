package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.StatisticService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStatisticRouting() {

    routing {
        authenticate {
            getStatistic()
            getStatisticDetails()
        }
    }
}

fun Route.getStatistic() {

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

fun Route.getStatisticDetails() {

    get("/statistic/details") {
        manager {
            call.respondNotFound()
        }
    }
}
