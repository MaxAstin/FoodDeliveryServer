package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.PatchOrder
import com.bunbeauty.fooddelivery.data.model.order.PostOrder
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.order.IOrderService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureOrderRouting() {

    routing {
        authenticate {
            getOrders()
            postOrder()
            patchOrder()
            observeClientOrders()
            observeManagerOrders()
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

fun Route.observeClientOrders() {

    val orderService: IOrderService by inject()
    val json: Json by inject()

    webSocket("/client/order/subscribe") {
        clientSocket(
            block = { request ->
                orderService.observeClientOrderUpdates(request.jwtUser.uuid).collect { clientOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(GetClientOrder.serializer(), clientOrder)))
                }
            },
            closeBlock = { jwtUser ->
                orderService.clientDisconnect(jwtUser.uuid)
            }
        )
    }
}

fun Route.observeManagerOrders() {

    val orderService: IOrderService by inject()
    val json: Json by inject()

    webSocket("/user/order/subscribe") {
        managerSocket(
            CAFE_UUID_PARAMETER,
            block = { request ->
                val cafeUuid = request.parameterMap[CAFE_UUID_PARAMETER]!!
                orderService.observeCafeOrderUpdates(cafeUuid).collect { cafeOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(GetCafeOrder.serializer(), cafeOrder)))
                }
            },
            closeBlock = { jwtUser ->
                orderService.clientDisconnect(jwtUser.uuid)
            }
        )
    }
}