package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.service.order.IOrderService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

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
