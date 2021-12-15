package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.food_delivery.service.statistic.IStatisticService
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
