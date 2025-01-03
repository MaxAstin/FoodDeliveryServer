package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone

val mapAddressV2: AddressV2.(DeliveryZone) -> GetAddressV2 = { deliveryZone ->
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
        cityUuid = cityUuid,
        isVisible = isVisible
    )
}
