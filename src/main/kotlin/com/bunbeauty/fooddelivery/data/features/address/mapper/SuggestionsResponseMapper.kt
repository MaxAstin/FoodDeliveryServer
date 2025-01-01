package com.bunbeauty.fooddelivery.data.features.address.mapper

import com.bunbeauty.fooddelivery.data.features.address.remotemodel.SuggestionsResponse
import com.bunbeauty.fooddelivery.domain.feature.address.model.Suggestion

val mapSuggestionsResponse: SuggestionsResponse.() -> List<Suggestion> = {
    suggestions.map { suggestion ->
        Suggestion(
            fiasId = suggestion.data.streetFiasId,
            street = suggestion.data.streetWithType,
            settlement = suggestion.data.settlementWithType,
            city = suggestion.data.cityWithType,
            latitude = suggestion.data.latitude,
            longitude = suggestion.data.longitude,
        )
    }
}