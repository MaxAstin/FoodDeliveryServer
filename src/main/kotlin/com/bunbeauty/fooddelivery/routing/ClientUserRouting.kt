package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PatchClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.PostClientUserAuth
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import com.bunbeauty.fooddelivery.service.ip.IRequestService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureClientUserRouting() {

    routing {
        clientLogin()

        authenticate {
            sendCode()
            checkCode()
            createTestClientUserPhone()
            getTestClientUserPhones()
            getClient()
            getClientSettings()
            patchClientUser()
        }
    }
}

fun Routing.clientLogin() {

    val clientUserService: IClientUserService by inject()

    post("/client/login") {
        safely {
            val postClientUserAuth: PostClientUserAuth = call.receive()
            val clientAuthResponse = clientUserService.login(postClientUserAuth)
            if (clientAuthResponse == null) {
                call.respondBad("Unable to log in with provided credentials")
            } else {
                call.respondOk(clientAuthResponse)
            }
        }
    }
}

fun Route.sendCode() {

    val clientUserService: IClientUserService by inject()
    val requestService: IRequestService by inject()

    post("/client/code_request") {
        limitRequestNumber(requestService) {
            clientWithBody<PostClientCodeRequest, GetClientUserLoginSessionUuid> { bodyRequest ->
                clientUserService.sendCode(bodyRequest.body)
            }
        }
    }
}

fun Route.checkCode() {

    val clientUserService: IClientUserService by inject()

    post("/client/code") {
        clientWithBody<PostClientCode, ClientAuthResponse> { bodyRequest ->
            clientUserService.checkCode(bodyRequest.body)
        }
    }
}

fun Route.createTestClientUserPhone() {

    val clientUserService: IClientUserService by inject()

    post("/test_phone") {
        adminWithBody<PostTestClientUserPhone, GetTestClientUserPhone> { bodyRequest ->
            clientUserService.createTestClientUserPhone(bodyRequest.body)
        }
    }
}

fun Route.getTestClientUserPhones() {

    val clientUserService: IClientUserService by inject()

    get("/test_phone") {
        admin {
            val testClientUserPhoneList = clientUserService.getTestClientUserPhoneList()
            call.respondOk(testClientUserPhoneList)
        }
    }
}

fun Route.getClient() {

    val clientUserService: IClientUserService by inject()

    get("/client") {
        client { request ->
            val clientUser = clientUserService.getClientUserByUuid(request.jwtUser.uuid)
            call.respondOkOrBad(clientUser)
        }
    }
}

fun Route.getClientSettings() {

    val clientUserService: IClientUserService by inject()

    get("/client/settings") {
        client { request ->
            val clientSettings = clientUserService.getClientSettingsByUuid(request.jwtUser.uuid)
            call.respondOkOrBad(clientSettings)
        }
    }
}

fun Route.patchClientUser() {

    val clientUserService: IClientUserService by inject()

    patch("/client") {
        clientWithBody<PatchClientUser, GetClientUser> { bodyRequest ->
            // Client activation is forbidden for clients
            if (bodyRequest.body.isActive == true) {
                null
            } else {
                val clientUserUuid = bodyRequest.request.jwtUser.uuid
                clientUserService.updateClientUserByUuid(clientUserUuid, bodyRequest.body)
            }
        }
    }
}