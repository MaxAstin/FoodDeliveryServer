package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.auth.authentication
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import java.sql.DriverManager.println

suspend inline fun DefaultWebSocketServerSession.clientSocket(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit
) {
    println("clientSocket")
    socket(block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun DefaultWebSocketServerSession.managerSocket(
    block: (Request) -> Unit,
    crossinline onSocketClose: (Request) -> Unit
) {
    println("managerSocket")
    socket(block = block, closeBlock = onSocketClose) { jwtUser ->
        jwtUser.isManager()
    }
}

@OptIn(DelicateCoroutinesApi::class)
suspend inline fun DefaultWebSocketServerSession.socket(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
    checkAccess: (JwtUser) -> Boolean
) {
    val jwtUser = call.authentication.principal() as? JwtUser
    if (jwtUser == null) {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
        return
    }

    if (!checkAccess(jwtUser)) {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No access for your role"))
        return
    }

    val request = Request(jwtUser)
    try {
        block(request)
        while (!incoming.isClosedForReceive) {
            delay(1000)
        }
    } finally {
        println("Socket closed ${closeReason.await()?.message}")
        closeBlock(request)
    }
}
