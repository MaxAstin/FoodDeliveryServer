package com.bunbeauty.fooddelivery.data.entity.statistic

import com.bunbeauty.fooddelivery.data.table.CafeStatisticProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CafeStatisticProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CafeStatisticProductTable.name
    var photoLink: String by CafeStatisticProductTable.photoLink
    var productCount: Int by CafeStatisticProductTable.productCount
    var productProceeds: Int by CafeStatisticProductTable.productProceeds

    var cafeStatistic: CafeStatisticEntity by CafeStatisticEntity referencedOn CafeStatisticProductTable.cafeStatistic

    companion object : UUIDEntityClass<CafeStatisticProductEntity>(CafeStatisticProductTable)

}