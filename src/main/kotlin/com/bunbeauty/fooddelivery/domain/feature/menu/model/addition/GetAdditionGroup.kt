package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class GetAdditionGroup(
    val uuid: String,
    val name: String,
    val singleChoice: Boolean,
    val priority: Int,
    val isVisible: Boolean,
    val additions: List<GetMenuProductAddition>,
)