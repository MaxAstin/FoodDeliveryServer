package com.bunbeauty.fooddelivery.data.repo.city

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.InsertCity
import com.bunbeauty.fooddelivery.data.table.CityTable
import java.util.*

class CityRepository : ICityRepository {

    override suspend fun insertCity(insertCity: InsertCity): GetCity = query {
        CityEntity.new {
            name = insertCity.name
            timeZone = insertCity.timeZone
            company = CompanyEntity[insertCity.company]
            isVisible = insertCity.isVisible
        }.toCity()
    }

    override suspend fun getCityListByCompanyUuid(companyUuid: UUID): List<GetCity> = query {
        CityEntity.find {
            CityTable.company eq companyUuid
        }.map { cityEntity ->
            cityEntity.toCity()
        }.toList()
    }

    override suspend fun getCityByUuid(cityUuid: UUID): GetCity? = query {
        CityEntity.findById(cityUuid)?.toCity()
    }
}