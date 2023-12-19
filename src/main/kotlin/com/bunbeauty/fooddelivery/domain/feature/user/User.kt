package com.bunbeauty.fooddelivery.domain.feature.user

import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.feature.company.Company

class User(
    val uuid: String,
    val username: String,
    val passwordHash: String,
    val role: String,
    val city: City,
    val company: Company,
) {

    val companyUuid = company.uuid

}