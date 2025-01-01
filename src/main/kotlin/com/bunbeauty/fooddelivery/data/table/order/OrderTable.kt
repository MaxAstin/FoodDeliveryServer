package com.bunbeauty.fooddelivery.data.table.order

import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import com.bunbeauty.fooddelivery.data.table.cafe.CafeTable
import org.jetbrains.exposed.dao.id.UUIDTable

object OrderTable : UUIDTable() {

    val isDelivery = bool("isDelivery")
    val time = long("time")
    val code = varchar("code", 512)
    val addressDescription = varchar("addressDescription", 512).nullable()
    val addressStreet = varchar("addressStreet", 512).nullable()
    val addressHouse = varchar("addressHouse", 512).nullable()
    val addressFlat = varchar("addressFlat", 512).nullable()
    val addressEntrance = varchar("addressEntrance", 512).nullable()
    val addressFloor = varchar("addressFloor", 512).nullable()
    val addressComment = varchar("addressComment", 512).nullable()
    val comment = varchar("comment", 512).nullable()
    val deferredTime = long("deferredTime").nullable()
    val status = varchar("status", 512)
    val deliveryCost = integer("deliveryCost").nullable()
    val paymentMethod = varchar("paymentMethod", 512).nullable()
    val percentDiscount = integer("percentDiscount").nullable()
    val cafe = reference("cafe", CafeTable)
    val company = reference("company", CompanyTable).index()
    val clientUser = reference("clientUser", ClientUserTable).index()

    init {
        index(false, cafe, time)
    }
}
