package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.COUNT_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.PatchOrder
import com.bunbeauty.fooddelivery.data.model.order.PostOrder
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.order.IOrderService
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureOrderRouting() {

    routing {
        authenticate {
            getCafeOrders()
            getCafeOrderDetails()
            getClientOrders()
            postOrder()
            patchOrder()
            deleteOrder()
            observeClientOrders()
            observeManagerOrders()
        }
    }
}

fun Route.getCafeOrders() {

    val orderService: IOrderService by inject()

    get("/order") {
        manager {
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
            val orderList = orderService.getOrderListByCafeUuid(cafeUuid)
            call.respondOk(orderList)
        }
    }
}

fun Route.getCafeOrderDetails() {

    val orderService: IOrderService by inject()

    get("/order/details") {
        manager {
            val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            val order = orderService.getOrderByUuid(orderUuid)
            call.respondOkOrBad(order)
        }
    }
}

fun Route.getClientOrders() {

    val orderService: IOrderService by inject()

    get("/client/order") {
        client { request ->
            val count = call.parameters[COUNT_PARAMETER]?.toIntOrNull()
            val orderList = orderService.getOrderListByUserUuid(request.jwtUser.uuid, count)
            call.respondOk(orderList)
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
        managerWithBody<PatchOrder, GetCafeOrder> { bodyRequest ->
            val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            orderService.changeOrder(orderUuid, bodyRequest.body)
        }
    }
}

fun Route.deleteOrder() {

    val orderService: IOrderService by inject()

    delete("/order") {
        adminDelete { uuid ->
            orderService.deleteOrder(uuid)
        }
    }
}

fun Route.observeClientOrders() {

    val orderService: IOrderService by inject()
    val json: Json by inject()

    webSocket("/client/order/subscribe") {
        clientSocket(
            block = { request ->
                orderService.observeClientOrderUpdates(request.jwtUser.uuid).onEach { clientOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(GetClientOrder.serializer(), clientOrder)))
                }.launchIn(this)
            },
            closeBlock = { request ->
                orderService.clientDisconnect(request.jwtUser.uuid)
            }
        )
    }
}

fun Route.observeManagerOrders() {

    val orderService: IOrderService by inject()
    val json: Json by inject()

    webSocket("/user/order/subscribe") {
        managerSocket(
            block = {
                val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
                orderService.observeCafeOrderUpdates(cafeUuid).onEach { cafeOrder ->
                    println("observeManagerOrders send frame")
                    outgoing.send(Frame.Text(json.encodeToString(GetCafeOrder.serializer(), cafeOrder)))
                }.launchIn(this)
            },
            closeBlock = {
                val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
                orderService.userDisconnect(cafeUuid)
            }
        )
    }
}