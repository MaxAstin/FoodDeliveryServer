package com.bunbeauty.food_delivery

import com.bunbeauty.food_delivery.data.DatabaseFactory
import com.bunbeauty.food_delivery.di.orderModule
import com.bunbeauty.food_delivery.plugins.configureRouting
import com.bunbeauty.food_delivery.plugins.configureSerialization
import com.bunbeauty.food_delivery.plugins.configureSockets
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.modules

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080) {
        configureRouting()
        configureSockets()
        configureSerialization()
        install(Koin) {
            modules(orderModule)
        }
    }.start(wait = true)
}
