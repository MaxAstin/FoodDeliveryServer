package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.manager(
    block: (Request) -> Unit,
) {
    checkRights(block) { jwtUser ->
        jwtUser.isManager() || jwtUser.isAdmin()
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getManagerWithListResult(block: (Request) -> List<R>) {
    manager { request ->
        val result = block(request)
        call.respondOkWithList(result)
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.managerWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> R?,
) {
    manager { request ->
        handleRequestWithBody(request, errorMessage, block)
    }
}

suspend inline fun <reified R> PipelineContext<Unit, ApplicationCall>.managerDelete(
    deleteBlock: (String) -> R?,
) {
    manager {
        delete(deleteBlock)
    }
}