package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.PostCity
import com.bunbeauty.food_delivery.service.city.ICityService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
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
        safely {
            val companyUuid = call.parameters["companyUuid"]
            if (companyUuid == null) {
                call.respond(HttpStatusCode.BadRequest, "Parameter companyUuid = null")
            } else {
                val cityList = cityService.getCityListByCompanyUuid(companyUuid)
                call.respond(HttpStatusCode.OK, cityList.toListWrapper())
            }

        }
    }
}

fun Route.postCity() {

    val cityService: ICityService by inject()

    post("/city") {
        adminPost<PostCity, GetCity> { jwtUser, postModel ->
            cityService.createCity(jwtUser.uuid, postModel)
        }
    }
}