package com.bunbeauty.fooddelivery.data.model.client_user

import java.util.UUID

class InsertClientUser(
    val phoneNumber: String,
    val email: String?,
    val companyUuid: UUID,
)