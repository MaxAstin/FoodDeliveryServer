package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.NonWorkingDayRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.non_working_day.GetNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.InsertNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.PatchNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.PostNonWorkingDay
import com.bunbeauty.fooddelivery.domain.model.non_working_day.UpdateNonWorkingDay
import com.bunbeauty.fooddelivery.domain.toUuid
import org.joda.time.DateTime

class NonWorkingDayService(
    private val nonWorkingDayRepository: NonWorkingDayRepository
) {

    suspend fun getNonWorkingDayListByCafeUuid(cafeUuid: String): List<GetNonWorkingDay> {
        val startOfDayMillis = DateTime.now().withTimeAtStartOfDay().millis

        return nonWorkingDayRepository.getNonWorkingDayListByCafeUuidAndTimestamp(
            cafeUuid = cafeUuid.toUuid(),
            timestamp = startOfDayMillis
        )
    }

    suspend fun createNonWorkingDay(postNonWorkingDay: PostNonWorkingDay): GetNonWorkingDay {
        val insertNonWorkingDay = InsertNonWorkingDay(
            timestamp = postNonWorkingDay.timestamp,
            cafeUuid = postNonWorkingDay.cafeUuid.toUuid()
        )
        return nonWorkingDayRepository.insertNonWorkingDay(insertNonWorkingDay)
    }

    suspend fun updateNonWorkingDayByUuid(uuid: String, patchNonWorkingDay: PatchNonWorkingDay): GetNonWorkingDay {
        return nonWorkingDayRepository.updateNonWorkingDay(
            uuid = uuid.toUuid(),
            updateNonWorkingDay = UpdateNonWorkingDay(
                isVisible = patchNonWorkingDay.isVisible
            )
        ).orThrowNotFoundByUuidError(uuid = uuid)
    }
}
