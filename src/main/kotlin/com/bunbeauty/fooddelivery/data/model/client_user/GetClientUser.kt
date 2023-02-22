package com.bunbeauty.fooddelivery.data.model.client_user

import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrder
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