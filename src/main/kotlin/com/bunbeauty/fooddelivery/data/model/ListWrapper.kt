package com.bunbeauty.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
class ListWrapper<T : Any>(
    val count: Int,
    val results: List<T>,
)