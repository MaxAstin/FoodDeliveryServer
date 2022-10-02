package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.street.GetStreet
import com.bunbeauty.fooddelivery.data.model.street.PostStreet
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.street.IStreetService
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

fun Routing.getStreetsByCityUuid() {

    val streetService: IStreetService by inject()

    get("/street") {
        safely {
            val cityUuid = call.parameters[CITY_UUID_PARAMETER] ?: error("$CITY_UUID_PARAMETER is required")
            val streetList = streetService.getStreetListByCompanyUuid(cityUuid)
            call.respondOk(streetList)
        }
    }
}

fun Route.postStreet() {

    val streetService: IStreetService by inject()

    post("/street") {
        managerWithBody<PostStreet, GetStreet> { bodyRequest ->
            streetService.createStreet(bodyRequest.body)
        }
    }

}
