package com.bunbeauty.fooddelivery.data.features.clientuser.mapper

import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight

val mapClientUserEntityToLight: ClientUserEntity.() -> ClientUserLight = {
    ClientUserLight(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
    )
}