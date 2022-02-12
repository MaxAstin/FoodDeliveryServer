package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.user.GetUser
import com.bunbeauty.fooddelivery.data.model.user.PostUser
import com.bunbeauty.fooddelivery.data.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondBad
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.user.IUserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserRouting() {

    routing {
        userLogin()

        authenticate {
            createUser()
        }
    }
}

fun Routing.userLogin() {

    val userService: IUserService by inject()

    post("/user/login") {
        safely {
            val postUserAuth: PostUserAuth = call.receive()
            val userAuthResponse = userService.login(postUserAuth)
            if (userAuthResponse == null) {
                call.respondBad("Unable to log in with provided credentials")
            } else {
                call.respondOk(userAuthResponse)
            }
        }
    }
}

fun Route.createUser() {

    val userService: IUserService by inject()

    post("/user") {
        adminWithBody<PostUser, GetUser> { bodyRequest ->
            userService.createUser(bodyRequest.body)
        }
    }
}

