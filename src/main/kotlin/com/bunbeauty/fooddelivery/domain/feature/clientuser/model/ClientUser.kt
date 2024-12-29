package com.bunbeauty.fooddelivery.domain.feature.clientuser.model

import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order

class ClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val isActive: Boolean,
    val company: Company,
    val addresses: List<Address>,
    val orders: List<Order>
)
