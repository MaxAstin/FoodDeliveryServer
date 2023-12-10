package com.bunbeauty.fooddelivery.data.features.address.remotemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AddressRequestBody(
    @SerialName("query") val query: String,
    @SerialName("from_bound") val fromBound: String,
    @SerialName("to_bound") val toBound: String,
    @SerialName("locations") val locations: String,
)