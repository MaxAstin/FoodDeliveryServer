package com.bunbeauty.food_delivery.data.model.client_user

import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.order.GetClientOrder
import kotlinx.serialization.Serializable

@Serializable
data class GetClientUser(
    val uuid: String,
    val phoneNumber: String,
    val email: String?,
    val company: GetCompany,
    val addresses: List<GetAddress>,
    val orders: List<GetClientOrder>,
)