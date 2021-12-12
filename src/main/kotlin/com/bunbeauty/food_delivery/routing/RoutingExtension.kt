package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.auth.JwtUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.safely(
    vararg parameterNameList: String,
    block: (List<String>) -> Unit,
) {
    try {
        val parameterList = parameterNameList.mapNotNull { parameterName ->
            call.parameters[parameterName]
        }
        if (parameterList.size == parameterNameList.size) {
            block(parameterList)
        } else {
            val nullParameterName = parameterNameList.find { parameterName ->
                call.parameters[parameterName] == null
            }
            call.respond(HttpStatusCode.BadRequest, "Parameter $nullParameterName is required")
        }
    } catch (exception: Exception) {
        call.respond(HttpStatusCode.BadRequest, "Exception: ${exception.message}")
        exception.printStackTrace()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.manager(block: (JwtUser) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.admin(block: (JwtUser) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isAdmin()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.client(block: (JwtUser) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.checkRights(
    block: (JwtUser) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    safely {
        val jwtUser = call.authentication.principal as? JwtUser
        if (jwtUser == null) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            if (checkBlock(jwtUser)) {
                block(jwtUser)
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

suspend inline fun <reified P, reified G> PipelineContext<Unit, ApplicationCall>.managerPost(postBlock: (JwtUser, P) -> G?) {
    manager { jwtUser ->
        handlePost(jwtUser, postBlock)
    }
}

suspend inline fun <reified P, reified G> PipelineContext<Unit, ApplicationCall>.adminPost(postBlock: (JwtUser, P) -> G?) {
    admin { jwtUser ->
        handlePost(jwtUser, postBlock)
    }
}

suspend inline fun <reified P, reified G> PipelineContext<Unit, ApplicationCall>.clientPost(postBlock: (JwtUser, P) -> G?) {
    client { jwtUser ->
        handlePost(jwtUser, postBlock)
    }
}

suspend inline fun <reified P, reified G> PipelineContext<Unit, ApplicationCall>.handlePost(
    jwtUser: JwtUser,
    postBlock: (JwtUser, P) -> G?,
) {
    val postModel: P = call.receive()
    val getModel: G? = postBlock(jwtUser, postModel)
    if (getModel == null) {
        call.respond(HttpStatusCode.BadRequest, "Can't create")
    } else {
        call.respond(HttpStatusCode.Created, getModel)
    }
}

suspend inline fun <reified T: Any> ApplicationCall.respondOk(model: T) {
    respond(HttpStatusCode.OK, model)
}

suspend inline fun <reified T: Any> ApplicationCall.respondOk(list: List<T>) {
    respond(HttpStatusCode.OK, list.toListWrapper())
}

suspend inline fun ApplicationCall.respondBad(message: String) {
    respond(HttpStatusCode.BadRequest, message)
}