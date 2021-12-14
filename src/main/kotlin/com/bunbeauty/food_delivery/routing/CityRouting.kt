package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.PostCity
import com.bunbeauty.food_delivery.service.city.ICityService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCityRouting() {

    routing {
        getAllCities()
        authenticate {
            postCity()
        }
    }
}

fun Routing.getAllCities() {

    val cityService: ICityService by inject()

    get("/city") {
        safely(COMPANY_UUID_PARAMETER) { parameterMap ->
            val companyUuid = parameterMap[COMPANY_UUID_PARAMETER]!!
            val cityList = cityService.getCityListByCompanyUuid(companyUuid)
            call.respondOk(cityList)
        }
    }
}

fun Route.postCity() {

    val cityService: ICityService by inject()

    post("/city") {
        adminWithBody<PostCity, GetCity> { bodyRequest ->
            cityService.createCity(bodyRequest.request.jwtUser.uuid, bodyRequest.body)
        }
    }
}