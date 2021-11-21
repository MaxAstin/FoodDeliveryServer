package com.bunbeauty.food_delivery

import com.bunbeauty.food_delivery.data.DatabaseFactory
import com.bunbeauty.food_delivery.di.*
import com.bunbeauty.food_delivery.plugins.configureSerialization
import com.bunbeauty.food_delivery.plugins.configureSockets
import com.bunbeauty.food_delivery.routing.configureRouting
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080) {
        configureRouting()
        configureSockets()
        configureSerialization()
        install(Koin) {
            modules(orderModule, cityModule, cafeModule, categoryModule, menuProductModule)
        }
    }.start(wait = true)
}
