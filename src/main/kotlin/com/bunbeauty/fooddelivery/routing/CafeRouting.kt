package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.domain.model.cafe.PatchCafe
import com.bunbeauty.fooddelivery.domain.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.CafeService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCafeRouting() {

    routing {
        getCafesByCityUuid()
        authenticate {
            postCafe()
            patchCafe()
        }
    }
}

private fun Routing.getCafesByCityUuid() {

    val cafeService: CafeService by inject()

    get("/cafe") {
        safely {
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            val cafeList = cafeService.getCafeListByCityUuid(cityUuid)
            call.respondOk(cafeList)
        }
    }
}

private fun Route.postCafe() {

    val cafeService: CafeService by inject()

    post("/cafe") {
        adminWithBody<PostCafe, GetCafe> { bodyRequest ->
            cafeService.createCafe(bodyRequest.body)
        }
    }
}

private fun Route.patchCafe() {

    val cafeService: CafeService by inject()

    patch("/cafe") {
        managerWithBody<PatchCafe, GetCafe> { bodyRequest ->
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            cafeService.updateCafe(cafeUuid, bodyRequest.body)
        }
    }
}