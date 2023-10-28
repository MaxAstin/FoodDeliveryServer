package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.data.model.non_working_day.PostNonWorkingDay
import com.bunbeauty.fooddelivery.routing.extension.*
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
            deleteNonWorkingDay()
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

private fun Route.deleteNonWorkingDay() {

    val nonWorkingDayService: NonWorkingDayService by inject()

    delete("/non_working_day") {
        managerDelete { uuid ->
            nonWorkingDayService.deleteNonWorkingDayByUuid(uuid)
        }
    }
}