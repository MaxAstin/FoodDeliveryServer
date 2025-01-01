package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PostAdditionToGroup(
    val additionUuid: String,
    val groupUuid: String,
    val isSelected: Boolean,
    val isVisible: Boolean,
)