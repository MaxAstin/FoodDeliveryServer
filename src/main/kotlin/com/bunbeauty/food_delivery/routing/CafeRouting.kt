package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.cafe.PostCafe
import com.bunbeauty.food_delivery.service.cafe.ICafeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCafeRouting() {

    routing {
        getCafesByCityUuid()
        authenticate {
            postCafe()
        }
    }
}

fun Routing.getCafesByCityUuid() {

    val cafeService: ICafeService by inject()

    get("/cafe") {
        safely(CITY_UUID_PARAMETER) { parameterList ->
            val cityUuid = parameterList[0]
            val cafeList = cafeService.getCafeListByCityUuid(cityUuid)
            call.respondOk(cafeList)
        }
    }
}

fun Route.postCafe() {

    val cafeService: ICafeService by inject()

    post("/cafe") {
        adminPost<PostCafe, GetCafe> { _, postCafe ->
            cafeService.createCafe(postCafe)
        }
    }
}