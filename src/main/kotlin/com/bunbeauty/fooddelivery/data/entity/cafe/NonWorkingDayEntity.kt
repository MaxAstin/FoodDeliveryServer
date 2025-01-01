package com.bunbeauty.fooddelivery.data.entity.cafe

import com.bunbeauty.fooddelivery.data.table.cafe.NonWorkingDayTable
import com.bunbeauty.fooddelivery.domain.model.non_working_day.GetNonWorkingDay
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class NonWorkingDayEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var timestamp: Long by NonWorkingDayTable.timestamp
    var isVisible: Boolean by NonWorkingDayTable.isVisible

    var cafe: CafeEntity by CafeEntity referencedOn NonWorkingDayTable.cafe

    companion object : UUIDEntityClass<NonWorkingDayEntity>(NonWorkingDayTable)

    fun toNonWorkingDay() = GetNonWorkingDay(
        uuid = uuid,
        timestamp = timestamp,
        isVisible = isVisible,
        cafeUuid = cafe.uuid
    )
}
