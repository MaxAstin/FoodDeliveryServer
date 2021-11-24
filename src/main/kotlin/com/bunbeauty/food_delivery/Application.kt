package com.bunbeauty.food_delivery

import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.data.DatabaseFactory
import com.bunbeauty.food_delivery.di.*
import com.bunbeauty.food_delivery.plugins.configureSerialization
import com.bunbeauty.food_delivery.plugins.configureSockets
import com.bunbeauty.food_delivery.routing.configureRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080) {
        configureSockets()
        configureSerialization()
        install(Koin) {
            modules(
                authModule,
                userModule,
                companyModule,
                orderModule,
                cityModule,
                cafeModule,
                categoryModule,
                menuProductModule,
            )
        }
        install(Authentication) {
            jwt {
                val jwtService: IJwtService by inject()
                jwtService.configureAuth(this)
            }
        }
        configureRouting()
    }.start(wait = true)
}
