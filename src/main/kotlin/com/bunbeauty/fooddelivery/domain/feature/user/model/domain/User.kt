package com.bunbeauty.fooddelivery.domain.feature.user.model.domain

import com.bunbeauty.fooddelivery.domain.feature.city.CityWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.Company

class User(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val notificationToken: String?,
    val unlimitedNotification: Boolean,
    val cityWithCafes: CityWithCafes,
    val company: Company,
) {

    val companyUuid = company.uuid

}