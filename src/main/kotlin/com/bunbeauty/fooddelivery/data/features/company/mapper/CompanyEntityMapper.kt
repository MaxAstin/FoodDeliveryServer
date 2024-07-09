package com.bunbeauty.fooddelivery.data.features.company.mapper

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntityToCityWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment

val mapCompanyEntity: CompanyEntity.() -> Company = {
    Company(
        uuid = uuid,
        name = name,
        offset = 3,
        delivery = Delivery(
            forFree = forFreeDelivery,
            cost = deliveryCost,
        ),
        forceUpdateVersion = forceUpdateVersion,
        payment = Payment(
            phoneNumber = paymentPhoneNumber,
            cardNumber = paymentCardNumber,
        ),
        percentDiscount = percentDiscount,
        maxVisibleRecommendationCount = maxVisibleRecommendationCount,
        citiesWithCafes = cities.map(mapCityEntityToCityWithCafes),
    )
}