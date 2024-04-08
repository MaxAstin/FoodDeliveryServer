package com.bunbeauty.fooddelivery.domain.feature.company.mapper

import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion

val mapCompany: Company.() -> GetCompany = {
    GetCompany(
        uuid = uuid,
        name = name,
        offset = 3,
        delivery = GetDelivery(
            forFree = delivery.forFree,
            cost = delivery.cost
        ),
        forceUpdateVersion = GetForceUpdateVersion(
            version = forceUpdateVersion
        ),
        payment = payment.mapPayment(),
        percentDiscount = percentDiscount,
        maxVisibleRecommendationCount = maxVisibleRecommendationCount,
    )
}