package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.PostCafe
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.cafe.ICafeService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCafeRouting() {

    routing {
        getCafesByCityUuid()
        authenticate {
            postCafe()
        }
    }
}

fun Routing.getCafesByCityUuid() {

    val cafeService: ICafeService by inject()

    get("/cafe") {
        safely {
            val cityUuid = call.parameters[CITY_UUID_PARAMETER] ?: error("$CITY_UUID_PARAMETER is required")
            val cafeList = cafeService.getCafeListByCityUuid(cityUuid)
            call.respondOk(cafeList)
        }
    }
}

fun Route.postCafe() {

    val cafeService: ICafeService by inject()

    post("/cafe") {
        adminWithBody<PostCafe, GetCafe> { bodyRequest ->
            cafeService.createCafe(bodyRequest.body)
        }
    }
}