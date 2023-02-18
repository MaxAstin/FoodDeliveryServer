package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.StatisticProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StatisticDayProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by StatisticProductTable.name
    var photoLink: String by StatisticProductTable.photoLink
    var productCount: Int by StatisticProductTable.productCount
    var productProceeds: Int by StatisticProductTable.productProceeds

    var statistic: StatisticEntity by StatisticEntity referencedOn StatisticProductTable.statistic

    companion object : UUIDEntityClass<StatisticDayProductEntity>(StatisticProductTable)

}