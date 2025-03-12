package com.bunbeauty.fooddelivery.domain.feature.company

import com.bunbeauty.fooddelivery.domain.feature.city.CityWithCafes
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType

class CompanyWithCafes(
    val uuid: String,
    val name: String,
    val offset: Int,
    val delivery: Delivery,
    val forceUpdateVersion: Int,
    val payment: Payment,
    val percentDiscount: Int?,
    val maxVisibleRecommendationCount: Int,
    val isOpen: Boolean,
    val workType: WorkType,
    val citiesWithCafes: List<CityWithCafes>
)
