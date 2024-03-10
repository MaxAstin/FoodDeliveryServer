package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PostAdditionGroup(
    val groupName: String,
    val groupPriority: Int,
    val singleChoice: Boolean,
    val menuProductUuids: List<String>,
    val additionUuids: List<String>,
)