package com.bunbeauty.fooddelivery.data.features.address.remotemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AddressRequestBody(
    @SerialName("query") val query: String,
    @SerialName("from_bound") val fromBound: Bound,
    @SerialName("to_bound") val toBound: Bound,
    @SerialName("locations") val locations: List<Location>
)

@Serializable
class Bound(
    @SerialName("value") val value: String
)

@Serializable
class Location(
    @SerialName("city") val city: String
)
