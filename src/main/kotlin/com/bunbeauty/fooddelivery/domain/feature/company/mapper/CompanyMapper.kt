package com.bunbeauty.fooddelivery.domain.feature.company.mapper

import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion

val mapCompanyWithCafes: CompanyWithCafes.() -> GetCompany = {
    GetCompany(
        uuid = uuid,
        name = name,
        offset = 3,
        delivery = delivery.mapDelivery(),
        forceUpdateVersion = GetForceUpdateVersion(
            version = forceUpdateVersion
        ),
        payment = payment.mapPayment(),
        percentDiscount = percentDiscount,
        maxVisibleRecommendationCount = maxVisibleRecommendationCount,
        isOpen = isOpen
    )
}
