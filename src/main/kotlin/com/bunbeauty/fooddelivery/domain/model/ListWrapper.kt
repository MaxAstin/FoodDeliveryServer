package com.bunbeauty.fooddelivery.domain.model

import kotlinx.serialization.Serializable

@Serializable
class ListWrapper<T : Any>(
    val count: Int,
    val results: List<T>
)
