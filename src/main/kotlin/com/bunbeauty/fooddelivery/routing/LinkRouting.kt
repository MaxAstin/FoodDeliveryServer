package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.respondOkWithList
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.LinkService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureLinkRouting() {

    routing {
        getLinks()
    }
}

private fun Routing.getLinks() {

    val linkService: LinkService by inject()

    get("/link") {
        safely {
            val companyUuid = call.parameters[Constants.COMPANY_UUID_PARAMETER] ?: error("${Constants.COMPANY_UUID_PARAMETER} is required")
            val paymentMethodList = linkService.getLinksByCompanyUuid(companyUuid)
            call.respondOkWithList(paymentMethodList)
        }
    }
}