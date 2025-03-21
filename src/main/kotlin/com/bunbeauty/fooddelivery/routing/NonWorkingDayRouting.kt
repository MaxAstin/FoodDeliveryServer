package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.PatchNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.PostNonWorkingDay
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.service.NonWorkingDayService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureNonWorkingDayRouting() {
    routing {
        getNonWorkingDaysByCafeUuid()
        authenticate {
            postNonWorkingDay()
            patchNonWorkingDay()
        }
    }
}

private fun Routing.getNonWorkingDaysByCafeUuid() {
    val nonWorkingDayService: NonWorkingDayService by inject()

    get("/non_working_day") {
        getListResult {
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            nonWorkingDayService.getNonWorkingDayListByCafeUuid(cafeUuid = cafeUuid)
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

private fun Route.patchNonWorkingDay() {
    val nonWorkingDayService: NonWorkingDayService by inject()

    patch("/non_working_day") {
        managerWithBody<PatchNonWorkingDay, GetNonWorkingDay> { bodyRequest ->
            val uuid = call.getParameter(UUID_PARAMETER)
            nonWorkingDayService.updateNonWorkingDayByUuid(
                uuid = uuid,
                patchNonWorkingDay = bodyRequest.body
            )
        }
    }
}
