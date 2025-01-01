package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.service.LinkService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureLinkRouting() {
    routing {
        getLinks()
    }
}

private fun Routing.getLinks() {
    val linkService: LinkService by inject()

    get("/link") {
        getListResult {
            val companyUuid = call.getParameter(Constants.COMPANY_UUID_PARAMETER)
            linkService.getLinksByCompanyUuid(companyUuid = companyUuid)
        }
    }
}
