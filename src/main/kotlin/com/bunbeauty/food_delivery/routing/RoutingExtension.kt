package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.user.JwtUser
import com.bunbeauty.food_delivery.data.model.user.PostUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(block: () -> Unit) {
    try {
        block()
    } catch (exception: Exception) {
        call.respond(HttpStatusCode.BadRequest, "Exception: ${exception.message}")
        exception.printStackTrace()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.safelyWithAuth(block: (JwtUser) -> Unit) {
    safely {
        val jwtUser = call.authentication.principal as? JwtUser
        if (jwtUser == null) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            block(jwtUser)
        }
    }
}