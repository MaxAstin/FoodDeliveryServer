package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.feature.user.model.api.PutNotificationToken
import com.bunbeauty.fooddelivery.domain.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.PatchClientUserSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.PostClientUserAuth
import com.bunbeauty.fooddelivery.routing.extension.clientGetResult
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.withBody
import com.bunbeauty.fooddelivery.service.client_user.ClientUserService
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureClientUserRouting() {
    routing {
        clientLogin()

        authenticate {
            getClient()
            getClientSettings()
            patchClientUser()
            patchClientSettings()
            putNotificationToken()
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

private fun Route.putNotificationToken() {
    val clientUserService: ClientUserService by inject()

    put("/client/notification_token") {
        clientWithBody<PutNotificationToken, Unit> { bodyRequest ->
            clientUserService.updateNotificationToken(
                userUuid = bodyRequest.request.jwtUser.uuid,
                putNotificationToken = bodyRequest.body
            )
        }
    }
}
