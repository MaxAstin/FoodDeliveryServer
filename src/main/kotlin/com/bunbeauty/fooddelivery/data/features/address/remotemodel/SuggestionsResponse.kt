package com.bunbeauty.fooddelivery.data.features.address.remotemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SuggestionsResponse(
    @SerialName("suggestions") val suggestions: List<Suggestion>,
)

@Serializable
class Suggestion(
    @SerialName("data") val data: SuggestionData,
)

@Serializable
class SuggestionData(
    @SerialName("street_with_type") val streetWithType: String,
)