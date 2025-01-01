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

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.clientGetResult(block: (Request) -> R) {
    client { request ->
        getResult {
            block(request)
        }
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.clientGetListResult(
    block: (Request) -> List<R>
) {
    client { request ->
        getListResult {
            block(request)
        }
    }
}

suspend inline fun <reified B, reified R : Any> PipelineContext<Unit, ApplicationCall>.clientWithBody(
    block: (BodyRequest<B>) -> R,
) {
    client { request ->
        handleRequestWithBody(
            request = request,
            block = block
        )
    }
}