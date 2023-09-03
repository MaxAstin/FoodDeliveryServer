package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.routing.extension.client
import com.bunbeauty.fooddelivery.routing.extension.respondBad
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.service.payment.IPaymentService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configurePaymentRouting() {

    routing {
        authenticate {
            getPayment()
        }
    }
}

private fun Route.getPayment() {

    val paymentService: IPaymentService by inject()

    get("/payment") {
        client { request ->
            val payment = paymentService.getPaymentByClientUuid(request.jwtUser.uuid)
            if (payment == null) {
                call.respondBad("No payment for userUuid = ${request.jwtUser.uuid}")
            } else {
                call.respondOk(payment)
            }
        }
    }
}