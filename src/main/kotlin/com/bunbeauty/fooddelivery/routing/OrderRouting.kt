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
import com.bunbeauty.fooddelivery.routing.extension.clientGetListResult
import com.bunbeauty.fooddelivery.routing.extension.clientSocket
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import com.bunbeauty.fooddelivery.routing.extension.managerGetListResult
import com.bunbeauty.fooddelivery.routing.extension.managerGetResult
import com.bunbeauty.fooddelivery.routing.extension.managerSocket
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
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

@Deprecated("Use getClientOrdersV2")
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
            val orderUuid = call.getParameter(UUID_PARAMETER)
            orderService.changeOrder(orderUuid, bodyRequest.body)
        }
    }
}

@Deprecated("Use getClientOrdersV2")
private fun Route.getClientOrders() {
    val orderService: OrderService by inject()

    get("/client/order") {
        clientGetListResult { request ->
            val count = call.parameters[COUNT_PARAMETER]?.toIntOrNull()
            orderService.getOrderListByUserUuid(request.jwtUser.uuid, count)
        }
    }
}

private fun Route.getCafeOrders() {
    val orderService: OrderService by inject()

    get("/order") {
        managerGetListResult {
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
            orderService.getOrderListByCafeUuid(cafeUuid)
        }
    }
}

@Deprecated("Use getCafeOrderDetailsV2")
private fun Route.getCafeOrderDetails() {
    val orderService: OrderService by inject()

    get("/order/details") {
        managerGetResult {
            val orderUuid = call.getParameter(UUID_PARAMETER)
            orderService.getOrderByUuid(orderUuid)
        }
    }
}

@Deprecated("Use observeClientOrdersV2")
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
                val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
                orderService.observeCafeOrderUpdates(cafeUuid).onEach { cafeOrder ->
                    outgoing.send(Frame.Text(json.encodeToString(cafeOrder)))
                }.launchIn(this)
            },
            onSocketClose = {
                val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)
                orderService.userDisconnect(cafeUuid)
            }
        )
    }
}

@Deprecated("Use postOrderV3")
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
        clientGetListResult { request ->
            val count = call.parameters[COUNT_PARAMETER]?.toIntOrNull()
            val uuid = call.parameters[UUID_PARAMETER]
            orderService.getOrderListByUserUuidV2(
                userUuid = request.jwtUser.uuid,
                count = count,
                orderUuid = uuid
            )
        }
    }
}

private fun Route.getCafeOrderDetailsV2() {
    val orderService: OrderService by inject()

    get("/v2/order/details") {
        managerGetResult {
            val orderUuid = call.getParameter(UUID_PARAMETER)
            orderService.getOrderByUuidV2(uuid = orderUuid)
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

@Deprecated("Used in 2.3.1 mobile app version, now need use getWorkInfo method with more information")
private fun Route.getIsOrderAvailable() {
    val orderService: OrderService by inject()

    get("/order_availability") {
        getResult {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            orderService.getOrderAvailability(companyUuid = companyUuid)
        }
    }
}
