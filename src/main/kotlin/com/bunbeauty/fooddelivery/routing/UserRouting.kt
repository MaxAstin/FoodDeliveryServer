package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.feature.user.PutNotificationToken
import com.bunbeauty.fooddelivery.domain.feature.user.PutUnlimitedNotification
import com.bunbeauty.fooddelivery.domain.feature.user.UserService
import com.bunbeauty.fooddelivery.domain.model.user.GetUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.domain.model.user.UserAuthResponse
import com.bunbeauty.fooddelivery.routing.extension.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserRouting() {

    routing {
        userLogin()

        authenticate {
            createUser()
            getUser()

            updateNotificationToken()
            clearNotificationToken()
            updateUnlimitedNotification()
        }
    }
}

private fun Routing.userLogin() {

    val userService: UserService by inject()

    post("/user/login") {
        withBody<PostUserAuth, UserAuthResponse>(errorMessage = "Unable to log in with provided credentials") { body ->
            userService.login(postUserAuth = body)
        }
    }
}

private fun Route.createUser() {

    val userService: UserService by inject()

    post("/user") {
        adminWithBody<PostUser, GetUser> { bodyRequest ->
            userService.createUser(postUser = bodyRequest.body)
        }
    }
}

private fun Route.getUser() {

    val userService: UserService by inject()

    get("/user") {
        manager { request ->
            respond<GetUser> {
                userService.getUser(userUuid = request.jwtUser.uuid)
            }
        }
    }
}

private fun Route.updateNotificationToken() {

    val userService: UserService by inject()

    put("/user/notification_token") {
        managerWithBody<PutNotificationToken> { bodyRequest ->
            userService.updateNotificationToken(
                userUuid = bodyRequest.request.jwtUser.uuid,
                putNotificationToken = bodyRequest.body
            )
        }
    }
}

private fun Route.clearNotificationToken() {

    val userService: UserService by inject()

    delete("/user/notification_token") {
        manager { request ->
            deleteByUserUuid(request = request) { userUuid ->
                userService.clearNotificationToken(userUuid = userUuid)
            }
        }
    }
}

private fun Route.updateUnlimitedNotification() {

    val userService: UserService by inject()

    put("/user/unlimited_notification") {
        managerWithBody<PutUnlimitedNotification> { bodyRequest ->
            userService.updateUnlimitedNotification(
                userUuid = bodyRequest.request.jwtUser.uuid,
                putUnlimitedNotification = bodyRequest.body
            )
        }
    }
}

