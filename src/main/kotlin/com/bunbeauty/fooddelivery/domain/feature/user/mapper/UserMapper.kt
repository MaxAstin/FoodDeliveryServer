package com.bunbeauty.fooddelivery.domain.feature.user.mapper

import com.bunbeauty.fooddelivery.domain.feature.city.mapper.mapCityWithCafes
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User
import com.bunbeauty.fooddelivery.domain.model.user.GetUser

fun User.toGetUser(): GetUser {
    return GetUser(
        uuid = uuid,
        username = username,
        passwordHash = passwordHash,
        role = role,
        unlimitedNotification = unlimitedNotification,
        city = cityWithCafes.mapCityWithCafes()
    )
}
