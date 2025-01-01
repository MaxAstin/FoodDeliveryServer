package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

import kotlinx.serialization.Serializable

@Serializable
class PostAdditionGroupToMenuProducts(
    val additionGroupUuid: String,
    val additionUuids: List<String>,
    val menuProductUuids: List<String>
)
