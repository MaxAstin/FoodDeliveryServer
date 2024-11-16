package com.bunbeauty.fooddelivery.domain.feature.user

import kotlinx.serialization.Serializable

@Serializable
class PutUnlimitedNotification(
    val isEnabled: Boolean
)