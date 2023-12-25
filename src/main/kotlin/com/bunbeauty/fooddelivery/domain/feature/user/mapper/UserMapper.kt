package com.bunbeauty.fooddelivery.domain.feature.user.mapper

import com.bunbeauty.fooddelivery.domain.feature.city.mapper.mapCityWithCafes
import com.bunbeauty.fooddelivery.domain.feature.user.User
import com.bunbeauty.fooddelivery.domain.model.user.GetUser

val mapUser: User.() -> GetUser = {
    GetUser(
        uuid = uuid,
        username = username,
        passwordHash = passwordHash,
        role = role,
        city = cityWithCafes.mapCityWithCafes()
    )
}