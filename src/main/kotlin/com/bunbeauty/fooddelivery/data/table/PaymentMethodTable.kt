package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object PaymentMethodTable : UUIDTable() {

    val name = OrderTable.varchar("name", 512)
    val value = OrderTable.varchar("value", 512).nullable()
    val valueToCopy = OrderTable.varchar("valueToCopy", 512).nullable()
    val company = OrderTable.reference("company", CompanyTable)

}