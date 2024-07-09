package com.bunbeauty.fooddelivery.data.features.address.remotemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AddressByIdRequestBody(
    @SerialName("query") val query: String
)