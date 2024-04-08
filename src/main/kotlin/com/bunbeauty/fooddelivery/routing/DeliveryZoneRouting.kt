package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.cafe.DeliveryZoneService
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.getAdminWithListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureDeliveryZoneRouting() {

    routing {
        authenticate {
            getDeliveryZoneByCafeUuid()
            postDeliveryZone()
        }
    }
}

private fun Route.getDeliveryZoneByCafeUuid() {

    val deliveryZoneService: DeliveryZoneService by inject()

    get("/delivery_zone") {
        getAdminWithListResult { request ->
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            deliveryZoneService.getDeliveryZoneListByCafeUuid(
                userUuid = request.jwtUser.uuid,
                cafeUuid = cafeUuid
            )
        }
    }
}

private fun Route.postDeliveryZone() {

    val deliveryZoneService: DeliveryZoneService by inject()

    post("/delivery_zone") {
        adminWithBody { bodyRequest ->
            deliveryZoneService.createDeliveryZone(
                userUuid = bodyRequest.request.jwtUser.uuid,
                postDeliveryZone = bodyRequest.body
            )
        }
    }
}
