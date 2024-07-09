package com.bunbeauty.fooddelivery.data.features.user

import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntityToCityWithCafes
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyEntity
import com.bunbeauty.fooddelivery.domain.feature.user.User

val mapUserEntity: UserEntity.() -> User = {
    User(
        uuid = uuid,
        username = username,
        passwordHash = passwordHash,
        role = role.roleName,
        cityWithCafes = city.mapCityEntityToCityWithCafes(),
        company = city.company.mapCompanyEntity(),
    )
}