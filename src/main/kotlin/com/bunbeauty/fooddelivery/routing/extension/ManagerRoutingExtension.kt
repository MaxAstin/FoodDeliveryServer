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

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.managerGetResult(
    block: (Request) -> R
) {
    manager { request ->
        getResult {
            block(request)
        }
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.managerGetListResult(
    block: (Request) -> List<R>
) {
    manager { request ->
        getListResult {
            block(request)
        }
    }
}

suspend inline fun <reified B, reified R: Any> PipelineContext<Unit, ApplicationCall>.managerWithBody(
    block: (BodyRequest<B>) -> R,
) {
    manager { request ->
        handleRequestWithBody(
            request = request,
            block = block
        )
    }
}