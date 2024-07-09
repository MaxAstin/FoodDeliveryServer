package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.respondBad
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.delivery.IDeliveryService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureDeliveryRouting() {

    routing {
        getDelivery()
    }
}

private fun Routing.getDelivery() {

    val deliveryService: IDeliveryService by inject()

    get("/delivery") {
        safely {
            val companyUuid = call.parameters[COMPANY_UUID_PARAMETER] ?: error("$COMPANY_UUID_PARAMETER is required")
            val delivery = deliveryService.getDeliveryByCompanyUuid(companyUuid)
            if (delivery == null) {
                call.respondBad("No delivery with companyUuid = $companyUuid")
            } else {
                call.respondOk(delivery)
            }
        }
    }
}