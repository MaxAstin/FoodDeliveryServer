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

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.adminGetResult(block: (Request) -> R) {
    admin { request ->
        getResult {
            block(request)
        }
    }
}

suspend inline fun <reified R : Any> PipelineContext<Unit, ApplicationCall>.getAdminWithListResult(block: (Request) -> List<R>) {
    admin { request ->
        getListResult {
            block(request)
        }
    }
}

suspend inline fun <reified B, reified R : Any> PipelineContext<Unit, ApplicationCall>.adminWithBody(
    block: (BodyRequest<B>) -> R
) {
    admin { request ->
        handleRequestWithBody(
            request = request,
            block = block
        )
    }
}
