package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.payment_method.IPaymentMethodService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configurePaymentMethodRouting() {

    routing {
        getPaymentMethods()
    }
}

fun Routing.getPaymentMethods() {

    val paymentMethodService: IPaymentMethodService by inject()

    get("/payment_method") {
        safely {
            val companyUuid = call.parameters[Constants.COMPANY_UUID_PARAMETER] ?: error("${Constants.COMPANY_UUID_PARAMETER} is required")
            val paymentMethodList = paymentMethodService.getPaymentMethodByCompanyUuid(companyUuid)
            call.respondOk(paymentMethodList)
        }
    }
}