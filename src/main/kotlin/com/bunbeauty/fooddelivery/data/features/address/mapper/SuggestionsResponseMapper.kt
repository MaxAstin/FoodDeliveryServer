package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.features.address.remotemodel.SuggestionsResponse

val mapSuggestionsResponse: SuggestionsResponse.() -> List<String> = {
    suggestions.map { suggestion ->
        suggestion.data.streetWithType
    }
}