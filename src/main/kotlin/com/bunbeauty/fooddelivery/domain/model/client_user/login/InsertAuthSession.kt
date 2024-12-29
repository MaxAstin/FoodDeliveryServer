package com.bunbeauty.fooddelivery.domain.model.client_user.login

import java.util.*

class InsertAuthSession(
    val phoneNumber: String,
    val time: Long,
    val attemptsLeft: Int,
    val isConfirmed: Boolean,
    val companyUuid: UUID
)
