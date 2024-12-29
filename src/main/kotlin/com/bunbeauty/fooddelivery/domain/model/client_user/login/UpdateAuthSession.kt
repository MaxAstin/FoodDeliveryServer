package com.bunbeauty.fooddelivery.domain.model.client_user.login

import java.util.*

class UpdateAuthSession(
    val uuid: UUID,
    val phoneNumber: String? = null,
    val time: Long? = null,
    val attemptsLeft: Int? = null,
    val isConfirmed: Boolean? = null
)
