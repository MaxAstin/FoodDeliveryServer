package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.error.ExceptionWithCode
import com.bunbeauty.fooddelivery.domain.toListWrapper
import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import java.sql.DriverManager.println

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(block: () -> Unit) {
    println("request ${context.request.path()}")
    try {
        block()
    } catch (exception: Exception) {
        when (exception) {
            is ExceptionWithCode -> {
                call.respond(
                    HttpStatusCode(
                        value = exception.code,
                        description = exception.message
                    ),
                    exception.message
                )
            }

            else -> {
                call.respondBad("Exception: ${exception.message}")
            }
        }

        println("Exception: ${exception.message}")
        exception.printStackTrace()
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getWithResult(block: () -> R) {
    safely {
        val result = block()
        call.respondOk(result)
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getWithListResult(block: () -> List<R>) {
    safely {
        val result = block()
        call.respondOkWithList(result)
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.checkRights(
    block: (Request) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    safely {
        val jwtUser = call.authentication.principal() as? JwtUser
        if (jwtUser == null) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            if (checkBlock(jwtUser)) {
                block(Request(jwtUser = jwtUser))
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.withBody(
    errorMessage: String? = null,
    block: (B) -> R?,
) {
    safely {
        val bodyModel: B = call.receive()
        val getModel: R? = block(bodyModel)
        call.respondOkOrBad(model = getModel, errorMessage = errorMessage)
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.handleRequestWithBody(
    request: Request,
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> R?,
) {
    val body: B = call.receive()
    val result: R? = block(
        BodyRequest(
            request = request,
            body = body
        )
    )
    call.respondOkOrBad(
        model = result,
        errorMessage = errorMessage ?: "Something went wrong"
    )
}

suspend inline fun <reified R> PipelineContext<Unit, ApplicationCall>.delete(
    deleteBlock: (String) -> R?,
) {
    val uuid = call.getParameter(UUID_PARAMETER)
    val result = deleteBlock(uuid)
    call.respondOkOrBad(model = result)
}

val PipelineContext<Unit, ApplicationCall>.clientIp: String
    get() = call.request.origin.remoteAddress

suspend inline fun ApplicationCall.respondOk() {
    respond(HttpStatusCode.OK)
}

suspend inline fun <reified T : Any> ApplicationCall.respondOk(model: T) {
    respond(HttpStatusCode.OK, model)
}

suspend inline fun <reified T : Any> ApplicationCall.respondOkOrBad(
    model: T?,
    errorMessage: String? = null,
) {
    if (model == null) {
        respondBad(errorMessage ?: "Data not found")
    } else {
        respondOk(model)
    }
}

suspend inline fun <reified T : Any> ApplicationCall.respondOkWithList(list: List<T>) {
    respond(HttpStatusCode.OK, list.toListWrapper())
}

suspend inline fun ApplicationCall.respondBad(message: String) {
    respond(HttpStatusCode.BadRequest, message)
}

suspend inline fun ApplicationCall.respondNotFound() {
    respond(HttpStatusCode.NotFound)
}