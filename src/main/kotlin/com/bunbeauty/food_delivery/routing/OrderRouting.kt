package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.service.address.IAddressService
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
        }
    }
}

fun Route.getOrders() {

    val orderService: IOrderService by inject()

    get("/order") {
        manager(CAFE_UUID_PARAMETER) { request ->
            val cafeUuid = request.parameterList[0] as String
            val addressList = orderService.getOrderListByCafeUuid(cafeUuid)
            call.respondOk(addressList)
        }
    }
}

fun Route.postOrder() {

    val orderService: IOrderService by inject()

    post("/order") {
        clientPost<PostOrder, GetClientOrder> { jwtUser, postOrder ->
            orderService.createOrder(jwtUser.uuid, postOrder)
        }
    }
}