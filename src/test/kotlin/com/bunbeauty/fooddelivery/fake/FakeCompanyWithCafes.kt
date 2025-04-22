package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType

object FakeCompanyWithCafes {

    fun create(
        forFreeDelivery: Int,
        deliveryCost: Int,
        isOpen: Boolean = false,
        workType: WorkType = WorkType.DELIVERY_AND_PICKUP
    ): CompanyWithCafes {
        return CompanyWithCafes(
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

object FakeCompany {

    fun create(
        uuid: String = "uuid",
        forFreeDelivery: Int = 0,
        deliveryCost: Int = 0,
        isOpen: Boolean = false,
        workType: WorkType = WorkType.DELIVERY_AND_PICKUP
    ): Company {
        return Company(
            uuid = uuid,
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
            workType = workType
        )
    }
}
