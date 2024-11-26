package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import com.bunbeauty.fooddelivery.service.DiscountService
import io.ktor.server.application.*
import io.ktor.server.routing.*
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