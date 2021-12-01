package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.food_delivery.service.delivery.IDeliveryService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureDeliveryRouting() {

    routing {
        getDelivery()
    }
}

fun Routing.getDelivery() {

    val deliveryService: IDeliveryService by inject()

    get("/delivery") {
        safely(COMPANY_UUID_PARAMETER) { parameterList ->
            val companyUuid = parameterList[0]
            val delivery = deliveryService.getDeliveryByCompanyUuid(companyUuid)
            if (delivery == null) {
                call.respond(HttpStatusCode.BadRequest, "No delivery with companyUuid = $companyUuid")
            } else {
                call.respondOk(delivery)
            }
        }
    }
}