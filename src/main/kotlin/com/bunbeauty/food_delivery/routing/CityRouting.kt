package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.service.city.ICityService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCityRouting() {

    routing {
        getAllCities()
    }
}

fun Routing.getAllCities() {

    val cityService: ICityService by inject()

    get("/city") {
        safely {
            val cityList = cityService.getCityList()
            call.respond(HttpStatusCode.OK, cityList.toListWrapper())
        }
    }
}




