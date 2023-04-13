package com.bunbeauty.fooddelivery.data.entity.statistic

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.new_statistic.GetStatistic
import com.bunbeauty.fooddelivery.data.model.new_statistic.PeriodType
import com.bunbeauty.fooddelivery.data.table.CompanyStatisticTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CompanyStatisticEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var time: Long by CompanyStatisticTable.time
    var periodType: String by CompanyStatisticTable.periodType
    var orderCount: Int by CompanyStatisticTable.orderCount
    var orderProceeds: Int by CompanyStatisticTable.orderProceeds

    var company: CompanyEntity by CompanyEntity referencedOn CompanyStatisticTable.company

    companion object : UUIDEntityClass<CompanyStatisticEntity>(CompanyStatisticTable)

    fun toStatistic() = GetStatistic(
        uuid = uuid,
        time = time,
        periodType = PeriodType.valueOf(periodType),
        orderCount = orderCount,
        orderProceeds = orderProceeds,
    )

}