package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.data.model.non_working_day.InsertNonWorkingDay
import com.bunbeauty.fooddelivery.data.model.non_working_day.PostNonWorkingDay
import com.bunbeauty.fooddelivery.data.repo.CafeRepository
import com.bunbeauty.fooddelivery.data.repo.NonWorkingDayRepository
import com.bunbeauty.fooddelivery.error.orThrowNotFoundByUuidError
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class NonWorkingDayService(
    private val cafeRepository: CafeRepository,
    private val nonWorkingDayRepository: NonWorkingDayRepository,
) {

    suspend fun getNonWorkingDayListByCafeUuid(cafeUuid: String): List<GetNonWorkingDay> {
        val cafe = cafeRepository.getCafeByUuid(cafeUuid.toUuid()).orThrowNotFoundByUuidError(cafeUuid)
        val startOfDayMillis = DateTime.now(DateTimeZone.forOffsetHours(cafe.offset)).withTimeAtStartOfDay().millis

        return nonWorkingDayRepository.getNonWorkingDayListByCafeUuidAndTimestamp(
            cafeUuid = cafeUuid.toUuid(),
            timestamp = startOfDayMillis
        )
    }

    suspend fun createNonWorkingDay(postNonWorkingDay: PostNonWorkingDay): GetNonWorkingDay {
        val insertNonWorkingDay = InsertNonWorkingDay(
            timestamp = postNonWorkingDay.timestamp,
            cafeUuid = postNonWorkingDay.cafeUuid.toUuid(),
        )
        return nonWorkingDayRepository.insertNonWorkingDay(insertNonWorkingDay)
    }

}