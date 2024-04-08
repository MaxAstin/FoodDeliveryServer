package com.bunbeauty.fooddelivery.data.entity.company

import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.data.table.CompanyTable
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.delivery.GetDelivery
import com.bunbeauty.fooddelivery.domain.model.company.payment_method.GetPayment
import com.bunbeauty.fooddelivery.domain.model.company.update_version.GetForceUpdateVersion
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

    val cities: SizedIterable<CityEntity> by CityEntity referrersOn CityTable.company

    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)

    fun toCompany() = GetCompany(
        uuid = uuid,
        name = name,
        offset = 3,
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
        percentDiscount = percentDiscount,
        maxVisibleRecommendationCount = maxVisibleRecommendationCount,
    )
}