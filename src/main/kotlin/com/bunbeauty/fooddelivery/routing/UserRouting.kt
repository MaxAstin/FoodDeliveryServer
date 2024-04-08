package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.model.user.GetUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.domain.model.user.UserAuthResponse
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.withBody
import com.bunbeauty.fooddelivery.service.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserRouting() {

    routing {
        userLogin()

        authenticate {
            createUser()
        }
    }
}

private fun Routing.userLogin() {

    val userService: UserService by inject()

    post("/user/login") {
        withBody<PostUserAuth, UserAuthResponse>(errorMessage = "Unable to log in with provided credentials") { body ->
            userService.login(body)
        }
    }
}

private fun Route.createUser() {

    val userService: UserService by inject()

    post("/user") {
        adminWithBody<PostUser, GetUser> { bodyRequest ->
            userService.createUser(bodyRequest.body)
        }
    }
}

