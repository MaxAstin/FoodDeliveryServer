package com.bunbeauty.fooddelivery.routing.extension

import com.bunbeauty.fooddelivery.auth.JwtUser
import com.bunbeauty.fooddelivery.routing.model.Request
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend inline fun DefaultWebSocketServerSession.clientSocket(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
) {
    println("clientSocket")
    socket(*parameterNameList, block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun DefaultWebSocketServerSession.managerSocket(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
) {
    println("managerSocket")
    socket(*parameterNameList, block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun DefaultWebSocketServerSession.socket(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
    crossinline closeBlock: (Request) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    val jwtUser = call.authentication.principal as? JwtUser
    if (jwtUser != null) {
        call.handleParameters(*parameterNameList) { parameterMap ->
            val request = Request(jwtUser, parameterMap)
            try {
                if (checkBlock(jwtUser)) {
                    block(request)
                } else {
                    close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for clients"))
                }
                launch {
                    while (!incoming.isClosedForReceive) {
                        delay(1000)
                    }
                    println("onClose ${closeReason.await()?.message}")
                    closeBlock(request)
                }
            } catch (exception: Exception) {
                println("onClose ${closeReason.await()?.message}")
                closeBlock(request)
            }
        }
    } else {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
    }
}
