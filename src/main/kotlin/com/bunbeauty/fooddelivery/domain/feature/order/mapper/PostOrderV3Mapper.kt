package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.InsertOrderAddressV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.OrderInfoV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.InsertOrderV3
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.PostOrderV3

val mapPostOrderV3: PostOrderV3.(OrderInfoV2) -> InsertOrderV3 = { orderInfo ->
    InsertOrderV3(
        time = orderInfo.time,
        isDelivery = isDelivery,
        code = orderInfo.code,
        address = InsertOrderAddressV2(
            description = address.description,
            street = address.street,
            house = address.house,
            flat = address.flat,
            entrance = address.entrance,
            floor = address.floor,
            comment = address.comment
        ),
        comment = comment,
        deferredTime = deferredTime,
        status = OrderStatus.NOT_ACCEPTED.name,
        deliveryCost = orderInfo.deliveryCost,
        paymentMethod = paymentMethod,
        percentDiscount = orderInfo.percentDiscount,
        cafeUuid = orderInfo.cafeUuid,
        companyUuid = orderInfo.companyUuid,
        clientUserUuid = orderInfo.clientUserUuid,
        orderProductList = orderProducts.map(mapPostOrderProductV3)
    )
}
