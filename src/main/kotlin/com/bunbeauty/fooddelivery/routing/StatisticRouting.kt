package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.PERIOD_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.statistic.StatisticService
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.manager
import com.bunbeauty.fooddelivery.routing.extension.managerGetResult
import com.bunbeauty.fooddelivery.routing.extension.respondNotFound
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
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
        managerGetResult { request ->
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER]
            val period = call.getParameter(PERIOD_PARAMETER)
            statisticService.getStatisticList(
                userUuid = request.jwtUser.uuid,
                cafeUuid = cafeUuid,
                period = period
            )
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
        getListResult {
            statisticService.getLastMonthCompanyStatistic()
        }
    }
}
