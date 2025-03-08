package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone

val mapAddress: Address.() -> GetAddress = {
    GetAddress(
        uuid = uuid,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        street = street.mapStreet(),
        userUuid = userUuid,
        isVisible = isVisible
    )
}

val mapAddressToV2: Address.(DeliveryZone) -> GetAddressV2 = { deliveryZone ->
    GetAddressV2(
        uuid = uuid,
        street = street.name,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        minOrderCost = deliveryZone.minOrderCost,
        normalDeliveryCost = deliveryZone.normalDeliveryCost,
        forLowDeliveryCost = deliveryZone.forLowDeliveryCost,
        lowDeliveryCost = deliveryZone.lowDeliveryCost,
        userUuid = userUuid,
        cityUuid = street.cityUuid,
        isVisible = isVisible,
        cafeUuid = ""
    )
}
