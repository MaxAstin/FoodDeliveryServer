package com.bunbeauty.fooddelivery.plugins

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import java.time.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(10)
        timeout = Duration.ofSeconds(30)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
