package com.bunbeauty.fooddelivery.data.entity.statistic

import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.table.CafeStatisticTable
import com.bunbeauty.fooddelivery.domain.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.domain.model.new_statistic.PeriodType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CafeStatisticEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var time: Long by CafeStatisticTable.time
    var periodType: String by CafeStatisticTable.periodType
    var orderCount: Int by CafeStatisticTable.orderCount
    var orderProceeds: Int by CafeStatisticTable.orderProceeds

    var cafe: CafeEntity by CafeEntity referencedOn CafeStatisticTable.cafe

    companion object : UUIDEntityClass<CafeStatisticEntity>(CafeStatisticTable)

    fun toStatistic() = GetStatistic(
        uuid = uuid,
        time = time,
        periodType = PeriodType.valueOf(periodType),
        orderCount = orderCount,
        orderProceeds = orderProceeds
    )
}
