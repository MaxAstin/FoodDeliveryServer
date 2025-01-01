package com.bunbeauty.fooddelivery.domain.model.client_user

import java.util.*

class InsertClientUser(
    val phoneNumber: String,
    val email: String?,
    val companyUuid: UUID,
)