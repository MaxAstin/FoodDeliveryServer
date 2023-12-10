package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.city.CityService
import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import com.bunbeauty.fooddelivery.domain.model.city.PostCity
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOkWithList
import com.bunbeauty.fooddelivery.routing.extension.safely
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCityRouting() {

    routing {
        getAllCities()
        authenticate {
            postCity()
        }
    }
}

private fun Routing.getAllCities() {

    val cityService: CityService by inject()

    get("/city") {
        safely {
            val companyUuid = call.parameters[COMPANY_UUID_PARAMETER] ?: error("$COMPANY_UUID_PARAMETER is required")
            val cityList = cityService.getCityListByCompanyUuid(companyUuid)
            call.respondOkWithList(cityList)
        }
    }
}

private fun Route.postCity() {

    val cityService: CityService by inject()

    post("/city") {
        adminWithBody<PostCity, GetCity> { bodyRequest ->
            cityService.createCity(bodyRequest.request.jwtUser.uuid, bodyRequest.body)
        }
    }
}