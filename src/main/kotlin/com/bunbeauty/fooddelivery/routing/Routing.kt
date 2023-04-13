package com.bunbeauty.fooddelivery.routing

import io.ktor.server.application.*

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
    configurePaymentRouting()
    configurePaymentMethodRouting()
}
