package com.bunbeauty.fooddelivery.data.features.user.mapper

import com.bunbeauty.fooddelivery.data.entity.UserEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapCafeWithZonesEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyWithCafesEntity
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import com.bunbeauty.fooddelivery.data.table.UserTable
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.User
import org.jetbrains.exposed.sql.ResultRow

fun UserEntity.toUser(): User {
    return User(
        uuid = uuid,
        username = username,
        passwordHash = passwordHash,
        role = role.roleName,
        notificationToken = notificationToken,
        unlimitedNotification = unlimitedNotification,
        cafeWithZones = cafe.mapCafeWithZonesEntity(),
        companyWithCafes = cafe.city.company.mapCompanyWithCafesEntity(),
        cityUuid = city.uuid
    )
}

//fun ResultRow.toUser(): User {
//    return User(
//        uuid = this[UserTable.id].value.toString(),
//        username = this[UserTable.username],
//        passwordHash = this[UserTable.passwordHash],
//        role = this[UserTable.role.roleName],
//        notificationToken = this[UserTable.notificationToken],
//        unlimitedNotification = this[UserTable.unlimitedNotification],
//        companyWithCafes = cafe.city.company.mapCompanyWithCafesEntity(),
//        cityUuid = city.uuid
//    )
//}
