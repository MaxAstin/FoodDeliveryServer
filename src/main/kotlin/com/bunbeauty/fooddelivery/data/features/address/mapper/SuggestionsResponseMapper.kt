package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.features.address.remotemodel.SuggestionsResponse
import com.bunbeauty.fooddelivery.domain.feature.address.model.Suggestion

val mapSuggestionsResponse: SuggestionsResponse.() -> List<Suggestion> = {
    suggestions.map { suggestion ->
        Suggestion(value = suggestion.data.streetWithType)
    }
}