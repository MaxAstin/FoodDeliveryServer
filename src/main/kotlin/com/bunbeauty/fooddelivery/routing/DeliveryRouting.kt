package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.delivery.DeliveryService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureDeliveryRouting() {

    routing {
        getDelivery()
    }
}

private fun Routing.getDelivery() {

    val deliveryService: DeliveryService by inject()

    get("/delivery") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val delivery = deliveryService.getDeliveryByCompanyUuid(companyUuid)
            call.respondOk(delivery)
        }
    }
}