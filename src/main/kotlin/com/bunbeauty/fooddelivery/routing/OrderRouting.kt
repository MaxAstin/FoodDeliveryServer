package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.COUNT_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.order.OrderService
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PatchOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.PostOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetClientOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.PostOrderV3
import com.bunbeauty.fooddelivery.routing.extension.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureOrderRouting() {

    routing {
        getIsOrderAvailable()

        authenticate {
            postOrder()
            patchOrder()
            getClientOrders()
            getCafeOrders()
            getCafeOrderDetails()
            observeClientOrders()
            observeManagerOrders()

            postOrderV2()
            getClientOrdersV2()
            getCafeOrderDetailsV2()
            observeClientOrdersV2()

            postOrderV3()
        }
    }
}

private fun Route.postOrder() {

    val orderService: OrderService by inject()

    post("/order") {
        clientWithBody<PostOrder, GetClientOrder> { bodyRequest ->
            orderService.createOrder(
                bodyRequest.request.jwtUser.uuid,
                bodyRequest.body
            )
        }
    }
}

private fun Route.patchOrder() {

    val orderService: OrderService by inject()

    patch("/order") {
        managerWithBody<PatchOrder, GetCafeOrder> { bodyRequest ->
            val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            orderService.changeOrder(orderUuid, bodyRequest.body)
        }
    }
}

private fun Route.getClientOrders() {

    val orderService: OrderService by inject()

    get("/client/order") {
        client { request ->
            val count = call.parameters[COUNT_PARAMETER]?.toIntOrNull()
            val orderList = orderService.getOrderListByUserUuid(request.jwtUser.uuid, count)
            call.respondOkWithList(orderList)
        }
    }
}

private fun Route.getCafeOrders() {

    val orderService: OrderService by inject()

    get("/order") {
        manager {
            val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
            val orderList = orderService.getOrderListByCafeUuid(cafeUuid)
            call.respondOkWithList(orderList)
        }
    }
}

private fun Route.getCafeOrderDetails() {

    val orderService: OrderService by inject()

    get("/order/details") {
        manager {
            val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            val order = orderService.getOrderByUuid(orderUuid)
            call.respondOkOrBad(order)
        }
    }
}

private fun Route.observeClientOrders() {

    val orderService: OrderService by inject()
    val json: Json by inject()

    webSocket("/client/order/subscribe") {
        clientSocket(
            block = { request ->
                orderService.observeClientOrderUpdates(request.jwtUser.uuid).onEach { clientOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(clientOrder)))
                }.launchIn(this)
            },
            closeBlock = { request ->
                orderService.clientDisconnect(request.jwtUser.uuid)
            }
        )
    }
}

private fun Route.observeManagerOrders() {

    val orderService: OrderService by inject()
    val json: Json by inject()

    webSocket("/user/order/subscribe") {
        managerSocket(
            block = {
                val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
                orderService.observeCafeOrderUpdates(cafeUuid).onEach { cafeOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(cafeOrder)))
                }.launchIn(this)
            },
            onSocketClose = {
                val cafeUuid = call.parameters[CAFE_UUID_PARAMETER] ?: error("$CAFE_UUID_PARAMETER is required")
                orderService.userDisconnect(cafeUuid)
            }
        )
    }
}

private fun Route.postOrderV2() {

    val orderService: OrderService by inject()

    post("/v2/order") {
        clientWithBody<PostOrderV2, GetClientOrderV2> { bodyRequest ->
            orderService.createOrderV2(
                bodyRequest.request.jwtUser.uuid,
                bodyRequest.body
            )
        }
    }
}

private fun Route.getClientOrdersV2() {

    val orderService: OrderService by inject()

    get("/v2/client/order") {
        client { request ->
            val count = call.parameters[COUNT_PARAMETER]?.toIntOrNull()
            val uuid = call.parameters[UUID_PARAMETER]
            val orderList = orderService.getOrderListByUserUuidV2(request.jwtUser.uuid, count, uuid)
            call.respondOkWithList(orderList)
        }
    }
}

private fun Route.getCafeOrderDetailsV2() {

    val orderService: OrderService by inject()

    get("/v2/order/details") {
        manager {
            val orderUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            val order = orderService.getOrderByUuidV2(orderUuid)
            call.respondOkOrBad(order)
        }
    }
}

private fun Route.observeClientOrdersV2() {

    val orderService: OrderService by inject()
    val json: Json by inject()

    webSocket("/client/order/v2/subscribe") {
        clientSocket(
            block = { request ->
                orderService.observeClientOrderUpdatesV2(clientUserUuid = request.jwtUser.uuid).onEach { clientOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(clientOrder)))
                }.launchIn(this)
            },
            closeBlock = { request ->
                orderService.clientDisconnect(request.jwtUser.uuid)
            }
        )
    }
}

private fun Route.postOrderV3() {

    val orderService: OrderService by inject()

    post("/v3/order") {
        clientWithBody<PostOrderV3, GetClientOrderV2> { bodyRequest ->
            orderService.createOrderV3(
                bodyRequest.request.jwtUser.uuid,
                bodyRequest.body
            )
        }
    }
}

private fun Route.getIsOrderAvailable() {

    val orderService: OrderService by inject()

    get("/is_order_available") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val isAvailable = orderService.isOrderAvailable(companyUuid = companyUuid)
            call.respondOk(isAvailable)
        }
    }
}