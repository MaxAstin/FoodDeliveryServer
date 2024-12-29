package com.bunbeauty.fooddelivery.data.features.clientuser.mapper

import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.data.features.order.mapper.mapOrderEntity
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight

val mapClientUserEntityToLight: ClientUserEntity.() -> ClientUserLight = {
    ClientUserLight(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email
    )
}

val mapClientUserEntity: ClientUserEntity.() -> ClientUser = {
    ClientUser(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
        isActive = isActive,
        company = company.mapCompanyEntity(),
        addresses = addresses.map(mapAddressEntity),
        orders = orders.map(mapOrderEntity)
    )
}
