package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.company.Company

object FakeClientUser {

    fun create(company: Company): ClientUser {
        return ClientUser(
            uuid = "",
            phoneNumber = "+79009001234",
            email = "",
            isActive = true,
            company = company,
            addresses = emptyList(),
            orders = emptyList()
        )
    }
}
