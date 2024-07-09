package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.routing.model.BodyRequest
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.admin(block: (Request) -> Unit) {
    checkRights(block) { jwtUser ->
        jwtUser.isAdmin()
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getAdminWithResult(block: (Request) -> R) {
    admin { request ->
        val result = block(request)
        call.respondOk(result)
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getAdminWithListResult(block: (Request) -> List<R>) {
    admin { request ->
        val result = block(request)
        call.respondOkWithList(result)
    }
}

suspend inline fun <reified B, reified R> PipelineContext<Unit, ApplicationCall>.adminWithBody(
    errorMessage: String? = null,
    block: (BodyRequest<B>) -> R?,
) {
    admin { request ->
        handleRequestWithBody(request, errorMessage, block)
    }
}

suspend inline fun <reified R> PipelineContext<Unit, ApplicationCall>.adminDelete(
    deleteBlock: (String) -> R?,
) {
    admin {
        delete(deleteBlock)
    }
}