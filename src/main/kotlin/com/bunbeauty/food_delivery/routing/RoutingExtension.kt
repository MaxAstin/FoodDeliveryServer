package com.bunbeauty.food_delivery.routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Application.route(path: String, build: Route.() -> Unit) {
    routing {
        route(path, build)
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(block: () -> Unit) {
    try {
        block()
    } catch (exception: Exception) {
        call.respond(HttpStatusCode.BadRequest, "Exception: ${exception.message}")
    }
}