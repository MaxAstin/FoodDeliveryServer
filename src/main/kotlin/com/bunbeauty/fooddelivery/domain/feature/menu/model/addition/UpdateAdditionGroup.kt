package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class UpdateAdditionGroup(
    val name: String?,
    val singleChoice: Boolean?,
    val priority: Int?,
    val isVisible: Boolean?,
)