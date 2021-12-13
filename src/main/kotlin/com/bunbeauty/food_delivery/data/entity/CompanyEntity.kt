package com.bunbeauty.food_delivery.data.entity

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.delivery.GetDelivery
import com.bunbeauty.food_delivery.data.table.CityTable
import com.bunbeauty.food_delivery.data.table.CompanyTable
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

    val cities: SizedIterable<CityEntity> by CityEntity referrersOn CityTable.company

    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)

    fun toCompany() = GetCompany(
        uuid = uuid,
        name = name,
        delivery = GetDelivery(
            forFree = forFreeDelivery,
            cost = deliveryCost
        )
    )
}