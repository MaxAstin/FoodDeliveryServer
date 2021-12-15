package com.bunbeauty.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListWrapper<T : Any>(
    val count: Int,
    val results: List<T>,
)