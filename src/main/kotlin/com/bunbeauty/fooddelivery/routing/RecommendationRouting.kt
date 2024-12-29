package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.RecommendationService
import io.ktor.server.application.*
import io.ktor.server.routing.*
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
