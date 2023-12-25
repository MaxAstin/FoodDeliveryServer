package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.order.model.CalculatedOrderValues
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetClientOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetOrderAddressV2

val mapOrder: Order.(CalculatedOrderValues) -> GetClientOrder = { calculatedOrderValues ->
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
        oldTotalCost = calculatedOrderValues.oldTotalCost,
        newTotalCost = calculatedOrderValues.newTotalCost,
        clientUserUuid = clientUser.uuid,
        oderProductList = oderProducts.map(mapOrderProduct)
    )
}

val mapOrderToV2: Order.(CalculatedOrderValues) -> GetClientOrderV2 = { calculatedOrderValues ->
    GetClientOrderV2(
        uuid = uuid,
        code = code,
        status = status,
        time = time,
        timeZone = cafeWithCity.city.timeZone,
        isDelivery = isDelivery,
        deferredTime = deferredTime,
        address = GetOrderAddressV2(
            description = addressDescription,
            street = addressStreet,
            house = addressHouse,
            flat = addressFlat,
            entrance = addressEntrance,
            floor = addressFloor,
            comment = addressComment,
        ),
        comment = comment,
        deliveryCost = deliveryCost,
        oldTotalCost = calculatedOrderValues.oldTotalCost,
        newTotalCost = calculatedOrderValues.newTotalCost,
        paymentMethod = paymentMethod,
        percentDiscount = percentDiscount,
        clientUserUuid = clientUser.uuid,
        oderProductList = oderProducts.map(mapOrderProduct)
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