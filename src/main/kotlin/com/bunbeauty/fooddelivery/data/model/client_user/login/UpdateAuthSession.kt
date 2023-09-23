package com.bunbeauty.fooddelivery.data.model.client_user.login

import java.util.*

class UpdateAuthSession(
    val uuid: UUID,
    val phoneNumber: String? = null,
    val time: Long? = null,
    val attemptsLeft: Int? = null,
    val isConfirmed: Boolean? = null,
)
