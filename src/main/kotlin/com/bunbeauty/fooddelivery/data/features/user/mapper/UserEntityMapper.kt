package com.bunbeauty.fooddelivery.data.features.user.mapper

import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeWithZonesEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User

fun UserEntity.toUser(): User {
    return User(
        uuid = uuid,
        username = username,
        passwordHash = passwordHash,
        role = role.roleName,
        notificationToken = notificationToken,
        unlimitedNotification = unlimitedNotification,
        cafeWithZones = cafe.mapCafeWithZonesEntity(),
        company = cafe.city.company.mapCompanyEntity(),
        cityUuid = city.uuid
    )
}
