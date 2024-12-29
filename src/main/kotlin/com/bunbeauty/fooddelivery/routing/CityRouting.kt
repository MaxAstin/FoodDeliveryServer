package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.city.CityService
import com.bunbeauty.fooddelivery.domain.model.city.GetCity
import com.bunbeauty.fooddelivery.domain.model.city.PostCity
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
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
        getListResult {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            cityService.getCityListByCompanyUuid(companyUuid = companyUuid)
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
