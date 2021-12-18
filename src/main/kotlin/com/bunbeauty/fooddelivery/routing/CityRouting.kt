package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.PostCity
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.city.ICityService
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