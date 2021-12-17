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
    println("jwtUser $jwtUser")
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
                println("2 close Only for clients")
                close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for clients"))
            }
        } catch (exception: Exception) {
            closeBlock(jwtUser)
            println("3 onClose ${closeReason.await()}")
        }
    } else {
        println("4 Only for authorized users")
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
    }
}