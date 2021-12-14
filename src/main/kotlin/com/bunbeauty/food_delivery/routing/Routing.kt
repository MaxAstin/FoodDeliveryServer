package com.bunbeauty.food_delivery.routing

import io.ktor.application.*

fun Application.configureRouting() {
    configureUserRouting()
    configureCompanyRouting()
    configureCityRouting()
    configureCafeRouting()
    configureStreetRouting()
    configureCategoryRouting()
    configureMenuProductRouting()
    configureDeliveryRouting()
    configureAddressRouting()
    configureOrderRouting()
    configureDefaultRouting()
}
