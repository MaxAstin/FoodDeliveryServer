package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.PostRecommendation
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.RecommendationService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRecommendationRouting() {
    routing {
        getRecommendations()
        authenticate {
            postRecommendations()
        }
    }
}

private fun Routing.getRecommendations() {

    val recommendationService: RecommendationService by inject()

    get("/recommendation") {
        safely {
            val companyUuid = call.getParameter(Constants.COMPANY_UUID_PARAMETER)
            val menuProductList = recommendationService.getRecommendationListByCompanyUuid(companyUuid)
            call.respondOk(menuProductList)
        }
    }
}

private fun Route.postRecommendations() {

    val recommendationService: RecommendationService by inject()

    post("/recommendation") {
        managerWithBody<PostRecommendation, GetRecommendation> { bodyRequest ->
            recommendationService.createRecommendation(bodyRequest.body)
        }
    }
}