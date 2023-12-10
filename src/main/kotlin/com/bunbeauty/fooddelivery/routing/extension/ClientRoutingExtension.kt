package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.client(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getClientWithResult(block: (Request) -> R) {
    client { request ->
        val result = block(request)
        call.respondOk(result)
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getClientWithListResult(block: (Request) -> List<R>) {
    client { request ->
        val result = block(request)
        call.respondOkWithList(result)
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.clientWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> R?,
) {
    client { request ->
        handleRequestWithBody(request, errorMessage, block)
    }
}