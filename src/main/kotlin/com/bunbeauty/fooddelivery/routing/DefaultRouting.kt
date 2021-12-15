package com.bunbeauty.fooddelivery.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureDefaultRouting() {

    routing {
        get("/") {
            call.respondText("Hello FoodDeliveryApi!")
        }
    }
}