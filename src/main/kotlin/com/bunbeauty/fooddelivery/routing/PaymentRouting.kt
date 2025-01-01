package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.routing.extension.client
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.service.payment.IPaymentService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
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
            call.respondOk(payment)
        }
    }
}
