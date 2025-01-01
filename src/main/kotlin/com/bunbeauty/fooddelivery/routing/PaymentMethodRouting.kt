package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.service.payment_method.IPaymentMethodService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configurePaymentMethodRouting() {
    routing {
        getPaymentMethods()
    }
}

private fun Routing.getPaymentMethods() {
    val paymentMethodService: IPaymentMethodService by inject()

    get("/payment_method") {
        getListResult {
            val companyUuid = call.getParameter(Constants.COMPANY_UUID_PARAMETER)
            paymentMethodService.getPaymentMethodByCompanyUuid(companyUuid = companyUuid)
        }
    }
}
