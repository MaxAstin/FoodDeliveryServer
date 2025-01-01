package com.bunbeauty.fooddelivery.data.entity.company

import com.bunbeauty.fooddelivery.data.entity.CityEntity
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
    var percentDiscount: Int? by CompanyTable.percentDiscount
    var maxVisibleRecommendationCount: Int by CompanyTable.maxVisibleRecommendationCount
    var isOpen: Boolean by CompanyTable.isOpen
    var workType: String by CompanyTable.workType
    val cities: SizedIterable<CityEntity> by CityEntity referrersOn CityTable.company

    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)
}
