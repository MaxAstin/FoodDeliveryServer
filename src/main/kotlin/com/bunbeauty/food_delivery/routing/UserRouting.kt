package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.client_user.PostClientUserAuth
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.PostUser
import com.bunbeauty.food_delivery.data.model.user.PostUserAuth
import com.bunbeauty.food_delivery.service.client_user.IClientUserService
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
        loginAdmin()
        loginClient()

        //createUserWithoutAuth()
        authenticate {
            getClient()
            createUser()
        }
    }
}

fun Routing.loginAdmin() {

    val userService: IUserService by inject()

    post("/user/login") {
        safely {
            val postUserAuth: PostUserAuth = call.receive()
            val token = userService.getToken(postUserAuth)
            if (token == null) {
                call.respondBad("Unable to log in with provided credentials")
            } else {
                call.respondOk(token)
            }
        }
    }
}

fun Routing.loginClient() {

    val clientUserService: IClientUserService by inject()

    post("/client/login") {
        safely {
            val postClientUserAuth: PostClientUserAuth = call.receive()
            val token = clientUserService.getToken(postClientUserAuth)
            if (token == null) {
                call.respondBad("Unable to log in with provided credentials")
            } else {
                call.respondOk(token)
            }
        }
    }
}

fun Route.createUser() {

    val userService: IUserService by inject()

    post("/user") {
        adminPost<PostUser, GetUser> { _, postUser ->
            userService.createUser(postUser)
        }
    }
}

fun Route.getClient() {

    val clientUserService: IClientUserService by inject()

    get("/client") {
        client { jwtUser ->
            val clientUser = clientUserService.getClientUserByUuid(jwtUser.uuid)
            if (clientUser == null) {
                call.respondBad("No user with such uuid")
            } else {
                call.respondOk(clientUser)
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

