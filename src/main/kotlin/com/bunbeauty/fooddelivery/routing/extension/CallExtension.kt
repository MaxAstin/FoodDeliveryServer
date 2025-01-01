package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.domain.error.parameterIsRequiredError
import io.ktor.server.application.ApplicationCall

fun ApplicationCall.getParameter(name: String): String {
    return parameters[name] ?: parameterIsRequiredError(name)
}
