package com.bunbeauty.fooddelivery.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(
            dataModule,
            authModule,
            userModule,
            clientUserModule,
            companyModule,
            orderModule,
            cityModule,
            cafeModule,
            streetModule,
            categoryModule,
            menuProductModule,
            hitModule,
            deliveryModule,
            addressModule,
            statisticModule,
            dateTimeModule,
            versionModule,
            firebaseModule,
            paymentModule,
            paymentMethodModule,
            linkModule,
            notificationModule,
            discountModule,
            requestModule,
            authorizationModule,
            recommendationModule,
            nonWorkingDayModule,
        )
    }
}
