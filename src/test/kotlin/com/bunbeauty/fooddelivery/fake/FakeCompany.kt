package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkType

object FakeCompany {

    fun create(
        forFreeDelivery: Int,
        deliveryCost: Int,
        isOpen: Boolean = false,
        workType: WorkType = WorkType.DELIVERY_AND_PICKUP
    ): Company {
        return Company(
            uuid = "uuid",
            name = "name",
            offset = 3,
            delivery = Delivery(
                forFree = forFreeDelivery,
                cost = deliveryCost
            ),
            forceUpdateVersion = 0,
            payment = Payment(
                phoneNumber = null,
                cardNumber = null
            ),
            percentDiscount = null,
            maxVisibleRecommendationCount = 0,
            isOpen = isOpen,
            citiesWithCafes = emptyList(),
            workType = workType
        )
    }
}
