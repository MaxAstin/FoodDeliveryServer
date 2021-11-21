package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.service.cafe.ICafeService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCafeRouting() {

    routing {
        getCategories()
    }
}

fun Routing.getCafesByCityUuid() {

    val cafeService: ICafeService by inject()

    get("/cafe/{cityUuid}") {
        safely {
            val cityUuid = call.parameters["cityUuid"]
            if (cityUuid == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                val cafeList = cafeService.getCafeListByCityUuid(cityUuid)
                call.respond(HttpStatusCode.OK, cafeList.toListWrapper())
            }
        }
    }
}