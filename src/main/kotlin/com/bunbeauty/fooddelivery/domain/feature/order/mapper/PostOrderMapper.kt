package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.InsertOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.OrderInfo
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrder

val mapPostOrder: PostOrder.(OrderInfo) -> InsertOrder = { orderInfo ->
    InsertOrder(
        time = orderInfo.time,
        isDelivery = isDelivery,
        code = orderInfo.code,
        addressDescription = addressDescription,
        comment = comment,
        deferredTime = deferredTime,
        status = OrderStatus.NOT_ACCEPTED.name,
        deliveryCost = orderInfo.deliveryCost,
        cafeUuid = orderInfo.cafeUuid,
        companyUuid = orderInfo.companyUuid,
        clientUserUuid = orderInfo.clientUserUuid,
        orderProductList = orderProducts.map(mapPostOrderProduct)
    )
}
