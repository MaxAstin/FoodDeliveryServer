package com.bunbeauty.fooddelivery.data.entity.company

import com.bunbeauty.fooddelivery.data.table.PaymentMethodTable
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPaymentMethod
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class PaymentMethodEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by PaymentMethodTable.name
    var value: String? by PaymentMethodTable.value
    var valueToCopy: String? by PaymentMethodTable.valueToCopy

    var company: CompanyEntity by CompanyEntity referencedOn PaymentMethodTable.company

    companion object : UUIDEntityClass<PaymentMethodEntity>(PaymentMethodTable)

    fun toPaymentMethod() = GetPaymentMethod(
        uuid = uuid,
        name = name,
        value = value,
        valueToCopy = valueToCopy,
    )
}