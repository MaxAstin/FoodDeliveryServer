package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import com.bunbeauty.fooddelivery.service.DiscountService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureDiscountRouting() {
    routing {
        getDiscount()
    }
}

private fun Routing.getDiscount() {
    val discountService: DiscountService by inject()

    get("/discount") {
        getResult {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            discountService.getDiscountByCompanyUuid(companyUuid = companyUuid)
        }
    }
}
