package com.bunbeauty.fooddelivery.data.features.city

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.features.city.mapper.mapCityEntity
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.model.city.InsertCity
import com.bunbeauty.fooddelivery.domain.toUuid

class CityRepository {

    suspend fun insertCity(insertCity: InsertCity): City = query {
        CityEntity.new {
            name = insertCity.name
            timeZone = insertCity.timeZone
            company = CompanyEntity[insertCity.company]
            isVisible = insertCity.isVisible
        }.mapCityEntity()
    }

    suspend fun getCityListByCompanyUuid(companyUuid: String): List<City> = query {
        CityEntity.find {
            CityTable.company eq companyUuid.toUuid()
        }.map(mapCityEntity)
            .toList()
    }

    suspend fun getCityByUuid(cityUuid: String): City? = query {
        CityEntity.findById(cityUuid.toUuid())?.mapCityEntity()
    }
}
