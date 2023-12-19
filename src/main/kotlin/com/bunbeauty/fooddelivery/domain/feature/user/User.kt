package com.bunbeauty.fooddelivery.domain.feature.user

import com.bunbeauty.fooddelivery.domain.feature.city.City

class User(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val city: City,
) {

    val company = city.company
    val companyUuid = company.uuid

}