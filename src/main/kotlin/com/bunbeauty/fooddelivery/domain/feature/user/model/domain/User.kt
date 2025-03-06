package com.bunbeauty.fooddelivery.domain.feature.user.model.domain

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithZones
import com.bunbeauty.fooddelivery.domain.feature.company.Company

class User(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val notificationToken: String?,
    val unlimitedNotification: Boolean,
    val cafeWithZones: CafeWithZones,
    val cityUuid: String,
    val company: Company
) {

    val companyUuid = company.uuid
}
