package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.NonWorkingDayEntity
import com.bunbeauty.fooddelivery.data.table.NonWorkingDayTable
import com.bunbeauty.fooddelivery.domain.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.InsertNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.UpdateNonWorkingDay
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import java.util.*

class NonWorkingDayRepository {

    suspend fun getNonWorkingDayListByCafeUuidAndTimestamp(cafeUuid: UUID, timestamp: Long): List<GetNonWorkingDay> =
        query {
            NonWorkingDayEntity.find {
                (NonWorkingDayTable.cafe eq cafeUuid) and
                        (NonWorkingDayTable.timestamp greaterEq timestamp)
            }.orderBy(NonWorkingDayTable.timestamp to SortOrder.DESC)
                .map { nonWorkingDayEntity ->
                    nonWorkingDayEntity.toNonWorkingDay()
                }
        }

    suspend fun insertNonWorkingDay(insertNonWorkingDay: InsertNonWorkingDay): GetNonWorkingDay = query {
        val nonWorkingDayEntity = NonWorkingDayEntity.new {
            timestamp = insertNonWorkingDay.timestamp
            cafe = CafeEntity[insertNonWorkingDay.cafeUuid]
        }

        nonWorkingDayEntity.toNonWorkingDay()
    }

    suspend fun updateNonWorkingDay(
        uuid: UUID,
        updateNonWorkingDay: UpdateNonWorkingDay,
    ): GetNonWorkingDay? = query {
        NonWorkingDayEntity.findById(uuid)?.apply {
            isVisible = updateNonWorkingDay.isVisible ?: isVisible
        }?.toNonWorkingDay()
    }
}