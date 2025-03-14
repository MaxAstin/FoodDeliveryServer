package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.address.StreetService
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureStreetRouting() {
    routing {
        getStreetsByCityUuid()
        authenticate {
            postStreet()
        }
    }
}

private fun Routing.getStreetsByCityUuid() {
    val streetService: StreetService by inject()

    get("/street") {
        getListResult {
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            streetService.getStreetListByCityUuid(cityUuid = cityUuid)
        }
    }
}

private fun Route.postStreet() {
    val streetService: StreetService by inject()

    post("/street") {
        managerWithBody<PostStreet, GetStreet> { bodyRequest ->
            streetService.createStreet(postStreet = bodyRequest.body)
        }
    }
}
