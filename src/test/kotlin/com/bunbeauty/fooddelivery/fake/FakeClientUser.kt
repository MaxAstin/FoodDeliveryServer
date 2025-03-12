package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserWithOrders
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes

object FakeClientUser {

    fun create(companyWithCafes: CompanyWithCafes): ClientUserWithOrders {
        return ClientUserWithOrders(
            uuid = "",
            phoneNumber = "+79009001234",
            email = "",
            isActive = true,
            companyWithCafes = companyWithCafes,
            addresses = emptyList(),
            orders = emptyList()
        )
    }
}
