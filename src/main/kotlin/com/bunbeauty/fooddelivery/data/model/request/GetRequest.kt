package com.bunbeauty.fooddelivery.data.model.request

data class GetRequest(
    val ip: String,
    val name: String,
    val time: Long,
)
