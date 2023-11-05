package com.bunbeauty.fooddelivery.domain.model.company

import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion
import kotlinx.serialization.Serializable

@Serializable
class GetCompany(
    val uuid: String,
    val name: String,
    val offset: Int,
    val delivery: GetDelivery,
    val forceUpdateVersion: GetForceUpdateVersion,
    val payment: GetPayment,
    val percentDiscount: Int?,
    val maxVisibleRecommendationCount: Int,
)