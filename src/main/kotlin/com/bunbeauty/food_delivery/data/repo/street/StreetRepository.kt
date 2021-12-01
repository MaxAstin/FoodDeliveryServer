package com.bunbeauty.food_delivery.data.repo.street

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.CafeEntity
import com.bunbeauty.food_delivery.data.entity.StreetEntity
import com.bunbeauty.food_delivery.data.model.street.GetStreet
import com.bunbeauty.food_delivery.data.model.street.InsertStreet
import com.bunbeauty.food_delivery.data.table.CafeTable
import com.bunbeauty.food_delivery.data.table.CityTable
import com.bunbeauty.food_delivery.data.table.StreetTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.booleanLiteral
import org.jetbrains.exposed.sql.select
import java.util.*

class StreetRepository : IStreetRepository {

    override suspend fun insertStreet(insertStreet: InsertStreet): GetStreet = query {
        StreetEntity.new {
            name = insertStreet.name
            cafe = CafeEntity[insertStreet.cafeUuid]
            isVisible = insertStreet.isVisible
        }.toStreet()
    }

    override suspend fun getStreetListByCompanyUuid(companyUuid: UUID): List<GetStreet> = query {
        ((StreetTable leftJoin CafeTable) leftJoin CityTable).select {
            CityTable.company eq companyUuid and
                    StreetTable.isVisible eq booleanLiteral(true)
        }.map { resultRow ->
            resultRow.toStreet()
        }
    }

    fun ResultRow.toStreet() = GetStreet(
        uuid = this[StreetTable.id].toString(),
        name = this[StreetTable.name],
        cafeUuid = this[StreetTable.cafe].value.toString(),
        isVisible = this[StreetTable.isVisible],
    )
}