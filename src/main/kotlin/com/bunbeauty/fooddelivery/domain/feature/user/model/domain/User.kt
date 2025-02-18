package com.bunbeauty.fooddelivery.domain.feature.user.model.domain

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.company.Company

class User(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val notificationToken: String?,
    val unlimitedNotification: Boolean,
    val cafe: Cafe,
    val company: Company
) {

    val companyUuid = company.uuid
}
