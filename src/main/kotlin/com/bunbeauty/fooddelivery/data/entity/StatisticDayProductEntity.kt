package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.StatisticDayProductTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StatisticDayProductEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by StatisticDayProductTable.name
    var photoLink: String by StatisticDayProductTable.photoLink
    var productCount: Int by StatisticDayProductTable.productCount
    var productProceeds: Int by StatisticDayProductTable.productProceeds

    var statisticDay: StatisticDayEntity by StatisticDayEntity referencedOn StatisticDayProductTable.statisticDay

    companion object : UUIDEntityClass<StatisticDayProductEntity>(StatisticDayProductTable)

//    fun toOrderProduct() = GetOrderProduct(
//        uuid = uuid,
//        count = count,
//        name = name,
//        newPrice = newPrice,
//        oldPrice = oldPrice,
//        utils = utils,
//        nutrition = nutrition,
//        description = description,
//        comboDescription = comboDescription,
//        photoLink = photoLink,
//        barcode = barcode,
//        menuProduct = menuProduct.toMenuProduct(),
//        orderUuid = order.uuid
//    )
}