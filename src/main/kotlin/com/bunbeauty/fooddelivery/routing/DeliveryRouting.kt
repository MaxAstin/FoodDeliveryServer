package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.delivery.DeliveryService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureDeliveryRouting() {
    routing {
        getDelivery()
    }
}

private fun Routing.getDelivery() {
    val deliveryService: DeliveryService by inject()

    get("/delivery") {
        getResult {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            deliveryService.getDeliveryByCompanyUuid(companyUuid)
        }
    }
}
