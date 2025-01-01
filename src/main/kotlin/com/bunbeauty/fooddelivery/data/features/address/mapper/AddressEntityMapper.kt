package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.domain.feature.address.model.Address

val mapAddressEntity: AddressEntity.() -> Address = {
    Address(
        uuid = uuid,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        street = street.mapStreetEntity(),
        cityUuid = street.cafe.city.uuid,
        userUuid = clientUser.uuid,
        isVisible = isVisible
    )
}
