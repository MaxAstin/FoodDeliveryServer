package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetClientOrder
import com.bunbeauty.fooddelivery.data.model.order.PatchOrder
import com.bunbeauty.fooddelivery.data.model.order.PostOrder
import com.bunbeauty.fooddelivery.routing.extension.*
import com.bunbeauty.fooddelivery.service.order.IOrderService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
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
            getOrders()
            postOrder()
            patchOrder()
            deleteOrder()
            observeClientOrders()
            getClientOrdersSse()
            observeManagerOrders()
        }
    }
}

fun Route.getOrders() {

    val orderService: IOrderService by inject()

    get("/order") {
        manager {
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
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

fun Route.getClientOrdersSse() {

    val orderService: IOrderService by inject()
    val json: Json by inject()

    get("/client/order/sse") {
        client { request ->
            call.respondTextWriter(contentType = ContentType.Text.EventStream) {
                orderService.observeClientOrderUpdates(request.jwtUser.uuid).onEach { clientOrder ->
                    val clientOrderJson = json.encodeToString(GetClientOrder.serializer(), clientOrder)
                    println("sent $clientOrderJson")
                    write(clientOrderJson)
                    flush()
                }.launchIn(this@get)
            }
        }
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