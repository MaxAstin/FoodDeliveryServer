package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.service.order.IOrderService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    configureDefaultRouting()
    configureCityRouting()
    configureCafeRouting()
    configureCategoryRouting()
    configureMenuProductRouting()





    val orderService: IOrderService by inject()

    routing {
        get("/order/create") {
            val getOrder = orderService.createOrder(
                PostOrder(
                    isDelivery = true,
                    comment = "test comment",
                    address = "ул Понетекорва, 55А",
                    deferredTime = null,
                    addressUuid = null,
                    cafeUuid = null,
                    orderProducts = emptyList(),
                )
            )

            if (getOrder == null) {
                call.respond(HttpStatusCode.Conflict)
            } else {
                call.respond(HttpStatusCode.Created, getOrder)
            }
        }
    }
}
