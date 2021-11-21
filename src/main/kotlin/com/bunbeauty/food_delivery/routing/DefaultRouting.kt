package com.bunbeauty.food_delivery.routing

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