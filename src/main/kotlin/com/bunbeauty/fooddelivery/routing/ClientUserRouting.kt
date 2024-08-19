package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.model.client_user.*
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureClientUserRouting() {

    routing {
        clientLogin()

        authenticate {
            getClient()
            getClientSettings()
            patchClientUser()
            patchClientSettings()
        }
    }
}

private fun Routing.clientLogin() {

    val clientUserService: IClientUserService by inject()

    post("/client/login") {
        withBody<PostClientUserAuth, ClientAuthResponse> { postClientUserAuth ->
            clientUserService.login(postClientUserAuth)
        }
    }
}

private fun Route.getClient() {

    val clientUserService: IClientUserService by inject()

    get("/client") {
        getClientWithResult { request ->
            clientUserService.getClientUserByUuid(request.jwtUser.uuid)
        }
    }
}

private fun Route.getClientSettings() {

    val clientUserService: IClientUserService by inject()

    get("/client/settings") {
        client { request ->
            val clientSettings = clientUserService.getClientSettingsByUuid(request.jwtUser.uuid)
            call.respondOkOrBad(clientSettings)
        }
    }
}

private fun Route.patchClientUser() {

    val clientUserService: IClientUserService by inject()

    patch("/client") {
        clientWithBody<PatchClientUserSettings, GetClientUser> { bodyRequest ->
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

private fun Route.patchClientSettings() {

    val clientUserService: IClientUserService by inject()

    patch("/client/settings") {
        clientWithBody<PatchClientUserSettings, GetClientSettings> { bodyRequest ->
            // Client activation is forbidden for clients
            if (bodyRequest.body.isActive == true) {
                null
            } else {
                val clientUserUuid = bodyRequest.request.jwtUser.uuid
                clientUserService.updateClientUserSettingsByUuid(clientUserUuid, bodyRequest.body)
            }
        }
    }
}