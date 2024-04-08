package com.bunbeauty.fooddelivery.domain.feature.address.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.model.GetSuggestion
import com.bunbeauty.fooddelivery.domain.feature.address.model.Suggestion

val mapSuggestion: Suggestion.() -> GetSuggestion = {
    GetSuggestion(
        fiasId = fiasId,
        street = street,
        details = settlement ?: city ?: "",
    )
}