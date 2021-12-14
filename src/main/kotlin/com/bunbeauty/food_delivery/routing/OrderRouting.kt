package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.food_delivery.data.model.order.GetCafeOrder
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.PatchOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.service.order.IOrderService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureOrderRouting() {

    routing {
        authenticate {
            getOrders()
            postOrder()
            patchOrder()
        }
    }
}

fun Route.getOrders() {

    val orderService: IOrderService by inject()

    get("/order") {
        manager(CAFE_UUID_PARAMETER) { request ->
            val cafeUuid = request.parameterMap[CAFE_UUID_PARAMETER]!!
            val addressList = orderService.getOrderListByCafeUuid(cafeUuid)
            call.respondOk(addressList)
        }
    }
}

fun Route.postOrder() {

    val orderService: IOrderService by inject()

    post("/order") {
        clientWithBody<PostOrder, GetClientOrder> { bodyRequest ->
            orderService.createOrder(
                bodyRequest.request.jwtUser.uuid,
                bodyRequest.body
            )
        }
    }
}

fun Route.patchOrder() {

    val orderService: IOrderService by inject()

    patch("/order") {
        managerWithBody<PatchOrder, GetCafeOrder>(UUID_PARAMETER) { bodyRequest ->
            val orderUuid = bodyRequest.request.parameterMap[UUID_PARAMETER]!!
            orderService.changeOrder(orderUuid, bodyRequest.body)
        }
    }
}