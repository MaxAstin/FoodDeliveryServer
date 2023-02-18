package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.table.StatisticTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StatisticEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var time: Long by StatisticTable.time
    var periodType: String by StatisticTable.periodType
    var orderCount: Int by StatisticTable.orderCount
    var orderProceeds: Int by StatisticTable.orderProceeds

    var company: CompanyEntity by CompanyEntity referencedOn StatisticTable.company

    companion object : UUIDEntityClass<StatisticEntity>(StatisticTable)

    fun toStatistic() = GetStatistic(
        uuid = uuid,
        time = time,
        periodType = PeriodType.valueOf(periodType),
        orderCount = orderCount,
        orderProceeds = orderProceeds,
    )

}