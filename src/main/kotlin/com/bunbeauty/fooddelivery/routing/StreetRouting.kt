package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.address.StreetService
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOkWithList
import com.bunbeauty.fooddelivery.routing.extension.safely
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
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
        safely {
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            val streetList = streetService.getStreetListByCityUuid(cityUuid = cityUuid)
            call.respondOkWithList(streetList)
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
