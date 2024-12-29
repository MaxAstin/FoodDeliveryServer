package com.bunbeauty.fooddelivery.data.table

import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkType
import org.jetbrains.exposed.dao.id.UUIDTable

object CompanyTable : UUIDTable() {

    val name = varchar("name", 512).uniqueIndex()
    val forFreeDelivery = integer("forFreeDelivery")
    val deliveryCost = integer("deliveryCost")
    val forceUpdateVersion = integer("forceUpdateVersion").default(0)
    val paymentPhoneNumber = varchar("paymentPhoneNumber", 512).nullable()
    val paymentCardNumber = varchar("paymentCardNumber", 512).nullable()
    val percentDiscount = integer("percentDiscount").nullable()
    val maxVisibleRecommendationCount = integer("maxVisibleRecommendationCount").default(4)
    val isOpen = bool("isOpen").default(true)
    val workType = varchar("workType", 512).default(WorkType.DELIVERY_AND_PICKUP.name)
}
