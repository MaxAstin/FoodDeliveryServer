package com.bunbeauty.fooddelivery.domain.model.client_user

import com.bunbeauty.fooddelivery.domain.model.address.GetAddress
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.order.client.get.GetClientOrder
import kotlinx.serialization.Serializable

@Serializable
class GetClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val company: GetCompany,
    val addresses: List<GetAddress>,
    val orders: List<GetClientOrder>,
)