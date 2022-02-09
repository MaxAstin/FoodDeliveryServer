package com.bunbeauty.fooddelivery.routing

import io.ktor.application.*

fun Application.configureRouting() {
    configureUserRouting()
    configureClientUserRouting()
    configureCompanyRouting()
    configureCityRouting()
    configureCafeRouting()
    configureStreetRouting()
    configureCategoryRouting()
    configureMenuProductRouting()
    configureDeliveryRouting()
    configureAddressRouting()
    configureOrderRouting()
    configureStatisticRouting()
    configureVersionRouting()
    configureDefaultRouting()
}
