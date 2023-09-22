package com.bunbeauty.fooddelivery.data.model.client_user.login

import java.util.*

class UpdateAuthSession(
    val uuid: UUID,
    val phoneNumber: String,
    val time: Long,
    val isConfirmed: Boolean,
)
