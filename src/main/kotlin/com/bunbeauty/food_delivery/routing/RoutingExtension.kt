package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.auth.JwtUser
import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.routing.model.BodyRequest
import com.bunbeauty.food_delivery.routing.model.Request
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(
    vararg parameterNameList: String,
    block: (Map<String, String>) -> Unit,
) {
    try {
        val nullParameterName = parameterNameList.find { parameterName ->
            call.parameters[parameterName] == null
        }
        if (nullParameterName == null) {
            val nonNullableParameters = parameterNameList.mapNotNull { parameterName ->
                call.parameters[parameterName]
            }
            val parameterMap = parameterNameList.zip(nonNullableParameters).toMap()
            block(parameterMap)
        } else {
            call.respondBad("Parameter $nullParameterName is required")
        }
    } catch (exception: Exception) {
        call.respondBad("Exception: ${exception.message}")
        exception.printStackTrace()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.manager(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
) {
    checkRights(parameterNameList, block) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.admin(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
) {
    checkRights(parameterNameList, block) { jwtUser ->
        jwtUser.isAdmin()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.client(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
) {
    checkRights(parameterNameList, block) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.checkRights(
    parameterNameList: Array<out String>,
    block: (Request) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    safely(*parameterNameList) { parameterList ->
        val jwtUser = call.authentication.principal as? JwtUser
        if (jwtUser == null) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            if (checkBlock(jwtUser)) {
                block(
                    Request(
                        jwtUser = jwtUser,
                        parameterMap = parameterList
                    )
                )
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.managerWithBody(
    vararg parameterNameList: String,
    block: (BodyRequest<B>) -> G?,
) {
    manager(*parameterNameList) { request ->
        handleBody(request, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.adminWithBody(
    vararg parameterNameList: String,
    block: (BodyRequest<B>) -> G?,
) {
    admin(*parameterNameList) { request ->
        handleBody(request, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.clientWithBody(
    vararg parameterNameList: String,
    block: (BodyRequest<B>) -> G?,
) {
    client(*parameterNameList) { request ->
        handleBody(request, block)
    }
}

suspend inline fun <reified B, reified G> PipelineContext<Unit, ApplicationCall>.handleBody(
    request: Request,
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
        call.respondBad("Something went wrong")
    } else {
        call.respondOk(getModel)
    }
}

suspend inline fun <reified T : Any> ApplicationCall.respondOk(model: T) {
    respond(HttpStatusCode.OK, model)
}

suspend inline fun <reified T : Any> ApplicationCall.respondOk(list: List<T>) {
    respond(HttpStatusCode.OK, list.toListWrapper())
}

suspend inline fun ApplicationCall.respondBad(message: String) {
    respond(HttpStatusCode.BadRequest, message)
}