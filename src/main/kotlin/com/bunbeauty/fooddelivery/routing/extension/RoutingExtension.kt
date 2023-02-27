package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.ext.toListWrapper
import com.bunbeauty.fooddelivery.data.model.request.RequestAvailability
import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import com.bunbeauty.fooddelivery.service.ip.IRequestService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import java.sql.DriverManager.println
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

suspend inline fun ApplicationCall.handleParameters(
    vararg parameterNameList: String,
    block: (Map<String, String>) -> Unit,
) {
    val nullParameterName = parameterNameList.find { parameterName ->
        parameters[parameterName] == null
    }
    if (nullParameterName == null) {
        val nonNullableParameters: List<String> = parameterNameList.mapNotNull { parameterName ->
            parameters[parameterName]
        }
        val parameterMap = parameterNameList.zip(nonNullableParameters).toMap()
        block(parameterMap)
    } else {
        respondBad("Parameter $nullParameterName is required")
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(block: () -> Unit) {
    println("request " + context.request.path())
    try {
        block()
    } catch (exception: Exception) {
        call.respondBad("Exception: ${exception.message}")
        exception.printStackTrace()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.manager(
    block: (Request) -> Unit,
) {
    checkRights(block) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.admin(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isAdmin()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.client(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isClient()
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

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.managerWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> G?,
) {
    manager { request ->
        handleBody(request, errorMessage, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.adminWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> G?,
) {
    admin { request ->
        handleBody(request, errorMessage, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.clientWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> G?,
) {
    client { request ->
        handleBody(request, errorMessage, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.withBody(
    errorMessage: String? = null,
    block: (B) -> G?,
) {
    safely {
        val bodyModel: B = call.receive()
        val getModel: G? = block(bodyModel)
        call.respondOkOrBad(model = getModel, errorMessage = errorMessage)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.handleBody(
    request: Request,
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> G?,
) {
    val bodyModel: B = call.receive()
    val getModel: G? = block(
        BodyRequest(
            request = request,
            body = bodyModel
        )
    )
    if (getModel == null) {
        call.respondBad(errorMessage ?: "Something went wrong")
    } else {
        call.respondOk(getModel)
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.adminDelete(deleteBlock: (String) -> Any?) {
    admin {
        val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
        val deletedObject = deleteBlock(orderUuid)
        if (deletedObject == null) {
            call.respondNotFound()
        } else {
            call.respondOk()
        }
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.limitRequestNumber(
    requestService: IRequestService,
    block: () -> Unit,
) {
    val ip = getIp()
    println("ip = $ip")
    when (val requestAvailability = requestService.isRequestAvailable(ip, call.request.path())) {
        is RequestAvailability.Available -> {
            block()
        }
        is RequestAvailability.NotAvailable -> {
            call.respondBad("Request is not available for more ${requestAvailability.seconds} seconds")
        }
    }
}

fun PipelineContext<Unit, ApplicationCall>.getIp(): String {
    return context::class.memberProperties
        .find { memberProperty ->
            memberProperty.name == "call"
        }?.let { callProperty ->
            callProperty.isAccessible = true
            val ip = (callProperty.getter.call(context) as NettyApplicationCall).request
                .context
                .pipeline()
                .channel()
                .remoteAddress()
                .toString()
            val regex = Regex("\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}")
            regex.find(ip)?.value
        } ?: ""
}

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
        respond(HttpStatusCode.BadRequest, errorMessage ?: "Data not found")
    } else {
        respondOk(model)
    }
}

suspend inline fun <reified T : Any> ApplicationCall.respondOk(list: List<T>) {
    respond(HttpStatusCode.OK, list.toListWrapper())
}

suspend inline fun ApplicationCall.respondBad(message: String) {
    respond(HttpStatusCode.BadRequest, message)
}

suspend inline fun ApplicationCall.respondNotFound() {
    respond(HttpStatusCode.NotFound)
}