package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.data.model.non_working_day.PostNonWorkingDay
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.NonWorkingDayService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureNonWorkingDayRouting() {

    routing {
        getNonWorkingDaysByCafeUuid()
        authenticate {
            postNonWorkingDay()
        }
    }
}

private fun Routing.getNonWorkingDaysByCafeUuid() {

    val nonWorkingDayService: NonWorkingDayService by inject()

    get("/non_working_day") {
        safely {
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            val nonWorkingDayList = nonWorkingDayService.getNonWorkingDayListByCafeUuid(cafeUuid)
            call.respondOk(nonWorkingDayList)
        }
    }
}

private fun Route.postNonWorkingDay() {

    val nonWorkingDayService: NonWorkingDayService by inject()

    post("/non_working_day") {
        managerWithBody<PostNonWorkingDay, GetNonWorkingDay> { bodyRequest ->
            nonWorkingDayService.createNonWorkingDay(bodyRequest.body)
        }
    }
}