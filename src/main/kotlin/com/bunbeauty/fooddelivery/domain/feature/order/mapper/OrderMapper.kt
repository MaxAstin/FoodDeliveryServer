package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.domain.feature.clientuser.mapper.mapClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe.GetCafeOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.cafe.GetCafeOrderDetails
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.cafe.GetCafeOrderDetailsV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetClientOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetOrderAddressV2

val mapOrder: Order.(OrderTotal) -> GetClientOrder = { orderTotal ->
    GetClientOrder(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription ?: compositeAddress,
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = orderTotal.oldTotalCost,
        newTotalCost = orderTotal.newTotalCost,
        clientUserUuid = clientUser.uuid,
        oderProductList = orderProducts.mapNotNull { oderProduct ->
            orderTotal.productTotalMap[oderProduct.uuid]?.let { orderProductTotal ->
                oderProduct.mapOrderProduct(uuid, orderProductTotal)
            }
        }
    )
}

val mapOrderToV2: Order.(OrderTotal) -> GetClientOrderV2 = { orderTotal ->
    GetClientOrderV2(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        address = addressV2,
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = orderTotal.oldTotalCost,
        newTotalCost = orderTotal.newTotalCost,
        paymentMethod = paymentMethod,
        percentDiscount = percentDiscount,
        clientUserUuid = clientUser.uuid,
        oderProductList = orderProducts.mapNotNull { oderProduct ->
            orderTotal.productTotalMap[oderProduct.uuid]?.let { orderProductTotal ->
                oderProduct.mapOrderProduct(uuid, orderProductTotal)
            }
        }
    )
}

val mapOrderToCafeOrder: Order.() -> GetCafeOrder = {
    GetCafeOrder(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        deferredTime = deferredTime,
        cafeUuid = cafeWithCity.cafe.uuid
    )
}

val mapOrderToCafeOrderDetails: Order.(OrderTotal) -> GetCafeOrderDetails = { orderTotal ->
    GetCafeOrderDetails(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        addressDescription = addressDescription ?: compositeAddress,
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = orderTotal.oldTotalCost,
        newTotalCost = orderTotal.newTotalCost,
        clientUser = clientUser.mapClientUserLight(),
        cafeUuid = cafeWithCity.cafe.uuid,
        oderProductList = orderProducts.mapNotNull { oderProduct ->
            orderTotal.productTotalMap[oderProduct.uuid]?.let { orderProductTotal ->
                oderProduct.mapOrderProduct(uuid, orderProductTotal)
            }
        },
        availableStatusList = availableStatusList
    )
}

val mapOrderToCafeOrderDetailsV2: Order.(OrderTotal) -> GetCafeOrderDetailsV2 = { orderTotal ->
    GetCafeOrderDetailsV2(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        address = addressV2,
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = orderTotal.oldTotalCost,
        newTotalCost = orderTotal.newTotalCost,
        clientUser = clientUser.mapClientUserLight(),
        cafeUuid = cafeWithCity.cafe.uuid,
        percentDiscount = percentDiscount,
        paymentMethod = paymentMethod,
        oderProductList = orderProducts.mapNotNull { oderProduct ->
            orderTotal.productTotalMap[oderProduct.uuid]?.let { orderProductTotal ->
                oderProduct.mapOrderProduct(uuid, orderProductTotal)
            }
        },
        availableStatusList = availableStatusList
    )
}

private val Order.compositeAddress: String
    get() {
        return addressStreet +
            getAddressPart(data = addressHouse, prefix = ", д. ") +
            getAddressPart(data = addressFlat, prefix = ", кв. ") +
            getAddressPart(data = addressEntrance, prefix = ", ", postfix = " подъезд") +
            getAddressPart(data = addressFloor, prefix = ", ", postfix = " этаж") +
            getAddressPart(data = addressComment, prefix = ", ")
    }

private fun getAddressPart(data: String?, prefix: String = "", postfix: String = ""): String {
    return if (data.isNullOrEmpty()) {
        ""
    } else {
        "$prefix$data$postfix"
    }
}

private val Order.availableStatusList: List<String>
    get() = buildList {
        add(OrderStatus.NOT_ACCEPTED.name)
        if (deferredTime != null) {
            add(OrderStatus.ACCEPTED.name)
        }
        add(OrderStatus.PREPARING.name)
        if (isDelivery) {
            add(OrderStatus.SENT_OUT.name)
        } else {
            add(OrderStatus.DONE.name)
        }
        add(OrderStatus.DELIVERED.name)
        add(OrderStatus.CANCELED.name)
    }

private val Order.addressV2: GetOrderAddressV2
    get() = GetOrderAddressV2(
        description = addressDescription,
        street = addressStreet,
        house = addressHouse,
        flat = addressFlat,
        entrance = addressEntrance,
        floor = addressFloor,
        comment = addressComment
    )
