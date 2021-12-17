package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend inline fun DefaultWebSocketServerSession.clientSocket(
    block: (JwtUser) -> Unit,
    crossinline closeBlock: (JwtUser) -> Unit,
) {
    val jwtUser = call.authentication.principal as? JwtUser
    if (jwtUser != null) {
        try {
            launch {
                while (!incoming.isClosedForReceive) {
                    delay(1000)
                }
                println("1 onClose ${closeReason.await()}")
                closeBlock(jwtUser)
            }
            if (jwtUser.isClient()) {
                block(jwtUser)
            } else {
                close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for clients"))
            }
        } catch (exception: Exception) {
            closeBlock(jwtUser)
            println("2 onClose ${closeReason.await()}")
        }
    } else {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
    }
}