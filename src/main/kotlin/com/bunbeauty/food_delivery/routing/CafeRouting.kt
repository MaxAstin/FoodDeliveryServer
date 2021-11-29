package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.PostCafe
import com.bunbeauty.food_delivery.service.cafe.ICafeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
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
            val cityUuid = call.parameters["cityUuid"]
            if (cityUuid == null) {
                call.respond(HttpStatusCode.BadRequest, "Parameter cityUuid = null")
            } else {
                val cafeList = cafeService.getCafeListByCityUuid(cityUuid)
                call.respond(HttpStatusCode.OK, cafeList.toListWrapper())
            }
        }
    }
}

fun Route.postCafe() {

    val cafeService: ICafeService by inject()

    post("/cafe") {
        adminPost<PostCafe, GetCafe> { _, postCafe ->
            cafeService.createCafe(postCafe)
        }
    }
}