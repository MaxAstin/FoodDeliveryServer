package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.cafe.CafeService
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PatchCafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
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

fun Application.configureCafeRouting() {
    routing {
        getCafesByCityUuid()
        getCafesByUuid()
        getWorkInfoV2()

        authenticate {
            postCafe()
            patchCafe()
        }
    }
}

private fun Routing.getCafesByCityUuid() {
    val cafeService: CafeService by inject()

    get("/cafe") {
        getListResult {
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            cafeService.getCafeListByCityUuid(cityUuid = cityUuid)
        }
    }
}

private fun Routing.getCafesByUuid() {
    val cafeService: CafeService by inject()

    get("/v2/cafe") {
        getResult {
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            cafeService.getCafeByCafeUuid(cafeUuid = cafeUuid)
        }
    }
}

private fun Route.postCafe() {
    val cafeService: CafeService by inject()

    post("/cafe") {
        adminWithBody<PostCafe, GetCafe> { bodyRequest ->
            cafeService.createCafe(
                userUuid = bodyRequest.request.jwtUser.uuid,
                postCafe = bodyRequest.body
            )
        }
    }
}

private fun Route.patchCafe() {
    val cafeService: CafeService by inject()

    patch("/cafe") {
        managerWithBody<PatchCafe, GetCafe> { bodyRequest ->
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            cafeService.updateCafe(
                userUuid = bodyRequest.request.jwtUser.uuid,
                cafeUuid = cafeUuid,
                patchCafe = bodyRequest.body
            )
        }
    }
}

/**
 * endpoint which describe cafe work
 * */
private fun Route.getWorkInfoV2() {
    val cafeService: CafeService by inject()

    get("/v2/work_info") {
        getResult {
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            cafeService.getWorkInfo(cafeUuid = cafeUuid)
        }
    }
}
