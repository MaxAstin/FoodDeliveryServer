package com.bunbeauty.fooddelivery.data.table

import org.jetbrains.exposed.dao.id.UUIDTable

object PaymentMethodTable : UUIDTable() {

    val name = PaymentMethodTable.varchar("name", 512)
    val value = PaymentMethodTable.varchar("value", 512).nullable()
    val valueToCopy = PaymentMethodTable.varchar("valueToCopy", 512).nullable()
    val company = PaymentMethodTable.reference("company", CompanyTable)

}