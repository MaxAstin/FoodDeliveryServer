package com.bunbeauty.food_delivery.data.repo.city

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CityEntity
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.model.city.GetCity
import com.bunbeauty.food_delivery.data.model.city.InsertCity
import com.bunbeauty.food_delivery.data.table.CityTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.booleanLiteral
import java.util.*

class CityRepository : ICityRepository {

    override suspend fun insertCity(insertCity: InsertCity): GetCity = query {
        CityEntity.new {
            name = insertCity.name
            offset = insertCity.offset
            company = CompanyEntity[insertCity.company]
            isVisible = insertCity.isVisible
        }.toCity()
    }

    override suspend fun getCityListByCompanyUuid(companyUuid: UUID): List<GetCity> = query {
        CityEntity.find {
            CityTable.company eq companyUuid and
                    CityTable.isVisible eq booleanLiteral(true)
        }.map { cityEntity ->
            cityEntity.toCity()
        }.toList()
    }
}