package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUserLoginSession
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientCode
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientCodeRequest
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.limitRequestNumber
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import com.bunbeauty.fooddelivery.service.ip.IRequestService
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureClientUserRouting() {

    routing {
        sendCode()
        checkCode()
    }
}

fun Routing.sendCode() {

    val clientUserService: IClientUserService by inject()
    val requestService: IRequestService by inject()

    post("/client/code_request") {
        limitRequestNumber(requestService) {
            clientWithBody<PostClientCodeRequest, GetClientUserLoginSession> { bodyRequest ->
                // TODO
                Any() as GetClientUserLoginSession
            }
        }
    }
}

fun Routing.checkCode() {

    val clientUserService: IClientUserService by inject()

    post("/client/code") {
        clientWithBody<PostClientCode, ClientAuthResponse> { bodyRequest ->
            // TODO
            Any() as ClientAuthResponse
        }
    }
}