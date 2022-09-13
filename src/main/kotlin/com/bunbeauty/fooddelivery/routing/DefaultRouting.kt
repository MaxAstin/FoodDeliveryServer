package com.bunbeauty.fooddelivery.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDefaultRouting() {

    routing {
        get("/") {
            call.respondText("Hello FoodDeliveryApi!")
        }
    }
}