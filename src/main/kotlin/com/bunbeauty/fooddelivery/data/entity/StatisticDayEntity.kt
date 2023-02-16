package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.statistic.GetStatisticDay
import com.bunbeauty.fooddelivery.data.table.StatisticDayTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StatisticDayEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var time: Long by StatisticDayTable.time
    var orderCount: Int by StatisticDayTable.orderCount
    var orderProceeds: Int by StatisticDayTable.orderProceeds

    var company: CompanyEntity by CompanyEntity referencedOn StatisticDayTable.company

    companion object : UUIDEntityClass<StatisticDayEntity>(StatisticDayTable)

    fun toStatisticDay() = GetStatisticDay(
        uuid = uuid,
        time = time,
        orderCount = orderCount,
        orderProceeds = orderProceeds,
    )

}