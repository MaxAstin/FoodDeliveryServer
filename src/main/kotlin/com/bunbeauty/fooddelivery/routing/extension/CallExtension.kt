package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.error.parameterIsRequiredError
import io.ktor.server.application.*

fun ApplicationCall.getParameter(name: String): String {
    return parameters[name] ?: parameterIsRequiredError(name)
}