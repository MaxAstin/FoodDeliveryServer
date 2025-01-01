package com.bunbeauty.fooddelivery.domain.model.client_user

import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.client.GetClientOrder
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
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