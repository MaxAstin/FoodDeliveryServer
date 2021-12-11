package com.bunbeauty.food_delivery.data.model.client_user

import java.util.*

data class InsertClientUser(
    val phoneNumber: String,
    val email: String?,
    val companyUuid: UUID,
)