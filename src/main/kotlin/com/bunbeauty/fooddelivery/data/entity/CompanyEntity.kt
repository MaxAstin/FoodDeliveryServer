package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.GetForceUpdateVersion
import com.bunbeauty.fooddelivery.data.model.company.GetPayment
import com.bunbeauty.fooddelivery.data.model.delivery.GetDelivery
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class CompanyEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var name: String by CompanyTable.name
    var forFreeDelivery: Int by CompanyTable.forFreeDelivery
    var deliveryCost: Int by CompanyTable.deliveryCost
    var forceUpdateVersion: Int by CompanyTable.forceUpdateVersion
    var paymentPhoneNumber: String? by CompanyTable.paymentPhoneNumber
    var paymentCardNumber: String? by CompanyTable.paymentCardNumber

    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)

    fun toCompany() = GetCompany(
        uuid = uuid,
        name = name,
        delivery = GetDelivery(
            forFree = forFreeDelivery,
            cost = deliveryCost
        ),
        forceUpdateVersion = GetForceUpdateVersion(
            version = forceUpdateVersion
        ),
        payment = GetPayment(
            phoneNumber = paymentPhoneNumber,
            cardNumber = paymentCardNumber
        ),
    )
}