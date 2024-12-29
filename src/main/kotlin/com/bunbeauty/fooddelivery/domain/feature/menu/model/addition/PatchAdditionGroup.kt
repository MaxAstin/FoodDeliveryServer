package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PatchAdditionGroup(
    val name: String? = null,
    val singleChoice: Boolean? = null,
    val priority: Int? = null,
    val isVisible: Boolean? = null
)
