package com.bunbeauty.fooddelivery.domain.feature.clientuser.model

import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order

class ClientUserWithOrders(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val isActive: Boolean,
    val companyWithCafes: CompanyWithCafes,
    val addresses: List<Address>,
    val orders: List<Order>
)
