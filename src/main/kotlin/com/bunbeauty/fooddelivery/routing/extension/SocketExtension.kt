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
    crossinline closeBlock: (JwtUser) -> Unit,
) {
    println("managerSocket")
    socket(*parameterNameList, block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isClient()
    }
}

suspend inline fun DefaultWebSocketServerSession.managerSocket(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
    crossinline closeBlock: (JwtUser) -> Unit,
) {
    println("managerSocket")
    socket(*parameterNameList, block = block, closeBlock = closeBlock) { jwtUser ->
        jwtUser.isManager()
    }
}

suspend inline fun DefaultWebSocketServerSession.socket(
    vararg parameterNameList: String,
    block: (Request) -> Unit,
    crossinline closeBlock: (JwtUser) -> Unit,
    checkBlock: (JwtUser) -> Boolean,
) {
    val jwtUser = call.authentication.principal as? JwtUser
    println("socket $jwtUser")
    if (jwtUser != null) {
        call.handleParameters(*parameterNameList) { parameterMap ->
            try {
                launch {
                    while (!incoming.isClosedForReceive) {
                        delay(1000)
                    }
                    println("onClose ${closeReason.await()}")
                    closeBlock(jwtUser)
                }
                if (checkBlock(jwtUser)) {
                    val nullParameterName = parameterNameList.find { parameterName ->
                        call.parameters[parameterName] == null
                    }
                    if (nullParameterName == null) {
                        val nonNullableParameters = parameterNameList.mapNotNull { parameterName ->
                            call.parameters[parameterName]
                        }
                        val parameterMap = parameterNameList.zip(nonNullableParameters).toMap()
                        block(Request(jwtUser, parameterMap))
                    } else {
                        call.respondBad("Parameter $nullParameterName is required")
                    }
                } else {
                    close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for clients"))
                }
            } catch (exception: Exception) {
                println("onClose ${closeReason.await()}")
                closeBlock(jwtUser)
            }
        }
    } else {
        close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Only for authorized users"))
    }
}
