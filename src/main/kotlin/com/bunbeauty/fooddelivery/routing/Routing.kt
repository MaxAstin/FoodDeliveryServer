package com.bunbeauty.fooddelivery.routing

import io.ktor.server.application.Application

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
    configureLinkRouting()
    configureNotificationRouting()
    configureDiscountRouting()
    configureAuthorizationRouting()
    configureRecommendationRouting()
    configureNonWorkingDayRouting()
    configureDeliveryZoneRouting()
    configureAdditionRouting()
}
