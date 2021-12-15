package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.fooddelivery.service.statistic.IStatisticService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStatisticRouting() {

    routing {
        authenticate {
            getStatistic()
        }
    }
}

fun Route.getStatistic() {

    val statisticService: IStatisticService by inject()

    get("/statistic") {
        manager(CAFE_UUID_PARAMETER, PERIOD_PARAMETER) { request ->
            val cafeUuid = request.parameterMap[CAFE_UUID_PARAMETER]!!
            val period = request.parameterMap[PERIOD_PARAMETER]!!
            val statisticList = statisticService.getStatisticList(request.jwtUser.uuid, cafeUuid, period)
            if (statisticList == null) {
                call.respondBad("Wrong parameters values")
            } else {
                call.respondOk(statisticList)
            }
        }
    }
}
