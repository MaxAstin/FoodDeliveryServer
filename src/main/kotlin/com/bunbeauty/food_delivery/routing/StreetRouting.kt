package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.model.street.GetStreet
import com.bunbeauty.food_delivery.data.model.street.PostStreet
import com.bunbeauty.food_delivery.service.street.IStreetService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStreetRouting() {

    routing {
        getStreets()
        authenticate {
            postStreet()
        }
    }
}

fun Routing.getStreets() {

    val streetService: IStreetService by inject()

    get("/street") {
        safely(COMPANY_UUID_PARAMETER) { parameterMap->
            val companyUuid = parameterMap[COMPANY_UUID_PARAMETER]!!
            val streetList = streetService.getStreetListByCompanyUuid(companyUuid)
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
