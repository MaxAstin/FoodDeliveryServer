package com.bunbeauty.fooddelivery.data.model.company.link

import kotlinx.serialization.Serializable

@Serializable
class GetLink(
    val uuid: String,
    val type: String,
    val value: String,
)