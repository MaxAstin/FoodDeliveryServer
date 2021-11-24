package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.user.JwtUser
import com.bunbeauty.food_delivery.data.model.user.PostAuth
import com.bunbeauty.food_delivery.data.model.user.PostUser
import com.bunbeauty.food_delivery.service.user.IUserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserRouting() {

    routing {
        login()
        createUserWithoutAuth()
        authenticate {
            //createUser()
        }
    }
}

fun Routing.login() {

    val userService: IUserService by inject()

    post("/login") {
        safely {
            val postAuth: PostAuth = call.receive()
            val token = userService.getToken(postAuth)
            if (token == null) {
                call.respond(HttpStatusCode.BadRequest, "Unable to log in with provided credentials")
            } else {
                call.respond(HttpStatusCode.OK, token)
            }
        }
    }
}

fun Route.createUser() {

    val userService: IUserService by inject()

    post("/user") {
        safelyWithAuth { userUuid ->
            val postUser: PostUser = call.receive()
            val user = userService.createUser(userUuid, postUser)
            if (user == null) {
                call.respond(HttpStatusCode.Forbidden)
            } else {
                call.respond(HttpStatusCode.Created, user)
            }
        }
    }
}

fun Route.createUserWithoutAuth() {

    val userService: IUserService by inject()

    post("/user") {
        safely {
            val postUser: PostUser = call.receive()
            val user = userService.createUser(postUser)
            call.respond(HttpStatusCode.Created, user)
        }
    }
}

