package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.model.client_user.*
import com.bunbeauty.fooddelivery.routing.extension.clientGetResult
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.withBody
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
        clientGetResult { request ->
            clientUserService.getClientUserByUuid(request.jwtUser.uuid)
        }
    }
}

private fun Route.getClientSettings() {

    val clientUserService: IClientUserService by inject()

    get("/client/settings") {
        clientGetResult { request ->
            clientUserService.getClientSettingsByUuid(request.jwtUser.uuid)
        }
    }
}

private fun Route.patchClientUser() {

    val clientUserService: IClientUserService by inject()

    patch("/client") {
        clientWithBody<PatchClientUserSettings, GetClientUser> { bodyRequest ->
            if (bodyRequest.body.isActive == true) {
                error("Client activation is forbidden for clients")
            } else {
                clientUserService.updateClientUserByUuid(
                    clientUserUuid = bodyRequest.request.jwtUser.uuid,
                    patchClientUser = bodyRequest.body
                )
            }
        }
    }
}

private fun Route.patchClientSettings() {

    val clientUserService: IClientUserService by inject()

    patch("/client/settings") {
        clientWithBody<PatchClientUserSettings, GetClientSettings> { bodyRequest ->
            if (bodyRequest.body.isActive == true) {
                error("Client activation is forbidden for clients")
            } else {
                clientUserService.updateClientUserSettingsByUuid(
                    clientUserUuid = bodyRequest.request.jwtUser.uuid,
                    patchClientUser = bodyRequest.body
                )
            }
        }
    }
}