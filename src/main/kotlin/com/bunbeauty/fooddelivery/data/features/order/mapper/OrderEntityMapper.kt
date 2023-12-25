package com.bunbeauty.fooddelivery.data.features.order.mapper

import com.bunbeauty.fooddelivery.data.entity.OrderEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeEntityToCafeWithCity
import com.bunbeauty.fooddelivery.data.features.clientuser.mapper.mapClientUserEntityToLight
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order

val mapOrderEntity: OrderEntity.() -> Order = {
    Order(
        uuid = uuid,
        time = time,
        isDelivery = isDelivery,
        code = code,
        addressDescription = addressDescription,
        addressStreet = addressStreet,
        addressHouse = addressHouse,
        addressFlat = addressFlat,
        addressEntrance = addressEntrance,
        addressFloor = addressFloor,
        addressComment = addressComment,
        comment = comment,
        deferredTime = deferredTime,
        status = status,
        deliveryCost = deliveryCost,
        paymentMethod = paymentMethod,
        percentDiscount = percentDiscount,
        cafeWithCity = cafe.mapCafeEntityToCafeWithCity(),
        company = company.mapCompanyEntity(),
        clientUser = clientUser.mapClientUserEntityToLight(),
        oderProducts = oderProducts.map(mapOrderProductEntity),
    )
}