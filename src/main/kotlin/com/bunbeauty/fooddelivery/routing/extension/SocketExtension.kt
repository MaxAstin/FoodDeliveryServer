package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.server.auth.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import java.sql.DriverManager.println

suspend inline fun DefaultWebSocketServerSession.clientSocket(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
) {
    println("clientSocket")
    socket(block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun DefaultWebSocketServerSession.managerSocket(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
) {
    println("managerSocket")
    socket(block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun DefaultWebSocketServerSession.socket(
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    val jwtUser = call.authentication.principal as? JwtUser
    if (jwtUser != null) {
        val request = Request(jwtUser)
        try {
            if (checkBlock(jwtUser)) {
                block(request)
            } else {
                close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for clients"))
            }
            while (!incoming.isClosedForReceive) {
                delay(1000)
            }
            println("onClose ${closeReason.await()?.message}")
            closeBlock(request)
        } catch (exception: Exception) {
            println("onClose ${closeReason.await()?.message}")
            closeBlock(request)
        }
    } else {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
    }
}
