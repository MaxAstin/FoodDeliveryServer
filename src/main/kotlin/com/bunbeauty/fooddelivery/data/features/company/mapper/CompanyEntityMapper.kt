package com.bunbeauty.fooddelivery.data.features.company.mapper

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntityToCityWithCafes
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import org.jetbrains.exposed.sql.ResultRow

val mapCompanyWithCafesEntity: CompanyEntity.() -> CompanyWithCafes = {
    CompanyWithCafes(
        uuid = uuid,
        name = name,
        offset = 3,
        delivery = Delivery(
            forFree = forFreeDelivery,
            cost = deliveryCost
        ),
        forceUpdateVersion = forceUpdateVersion,
        payment = Payment(
            phoneNumber = paymentPhoneNumber,
            cardNumber = paymentCardNumber
        ),
        percentDiscount = percentDiscount,
        maxVisibleRecommendationCount = maxVisibleRecommendationCount,
        isOpen = isOpen,
        workType = WorkType.valueOf(workType),
        citiesWithCafes = cities.map(mapCityEntityToCityWithCafes)
    )
}

val mapCompanyResultRow: ResultRow.() -> Company = {
    Company(
        uuid = this[CompanyTable.id].value.toString(),
        name = this[CompanyTable.name],
        offset = 3,
        delivery = Delivery(
            forFree = this[CompanyTable.forFreeDelivery],
            cost = this[CompanyTable.deliveryCost]
        ),
        forceUpdateVersion = this[CompanyTable.forceUpdateVersion],
        payment = Payment(
            phoneNumber = this[CompanyTable.paymentPhoneNumber],
            cardNumber = this[CompanyTable.paymentCardNumber]
        ),
        percentDiscount = this[CompanyTable.percentDiscount],
        maxVisibleRecommendationCount = this[CompanyTable.maxVisibleRecommendationCount],
        isOpen = this[CompanyTable.isOpen],
        workType = WorkType.valueOf(this[CompanyTable.workType])
    )
}
