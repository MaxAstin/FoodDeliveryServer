package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.RecommendationService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRecommendationRouting() {
    routing {
        getRecommendations()
    }
}

private fun Routing.getRecommendations() {
    val recommendationService: RecommendationService by inject()

    get("/recommendation") {
        safely {
            val companyUuid = call.getParameter(Constants.COMPANY_UUID_PARAMETER)
            val recommendationData = recommendationService.getRecommendationDataByCompanyUuid(companyUuid)
            call.respondOk(recommendationData)
        }
    }
}
