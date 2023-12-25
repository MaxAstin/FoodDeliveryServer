package com.bunbeauty.fooddelivery.data.features.company.mapper

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntityToCityWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.Company

val mapCompanyEntity: CompanyEntity.() -> Company = {
    Company(
        uuid = uuid,
        name = name,
        offset = 3,
        citiesWithCafes = cities.map(mapCityEntityToCityWithCafes),
    )
}