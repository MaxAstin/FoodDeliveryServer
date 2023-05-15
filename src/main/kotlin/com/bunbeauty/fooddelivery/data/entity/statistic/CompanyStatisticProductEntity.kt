package com.bunbeauty.fooddelivery.data.entity.statistic

import com.bunbeauty.fooddelivery.data.table.CompanyStatisticProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class CompanyStatisticProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CompanyStatisticProductTable.name
    var photoLink: String by CompanyStatisticProductTable.photoLink
    var productCount: Int by CompanyStatisticProductTable.productCount
    var productProceeds: Int by CompanyStatisticProductTable.productProceeds

    var companyStatistic: CompanyStatisticEntity by CompanyStatisticEntity referencedOn CompanyStatisticProductTable.companyStatistic

    companion object : UUIDEntityClass<CompanyStatisticProductEntity>(CompanyStatisticProductTable)

}