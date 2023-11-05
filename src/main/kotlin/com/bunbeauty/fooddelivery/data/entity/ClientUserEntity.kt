package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.data.table.OrderTable
import com.bunbeauty.fooddelivery.domain.model.client_user.GetCafeClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

class ClientUserEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by ClientUserTable.phoneNumber
    var email: String? by ClientUserTable.email
    var isActive: Boolean by ClientUserTable.isActive
    var company: CompanyEntity by CompanyEntity referencedOn ClientUserTable.company
    val addresses: SizedIterable<AddressEntity> by AddressEntity referrersOn AddressTable.clientUser
    val orders: SizedIterable<OrderEntity> by OrderEntity referrersOn OrderTable.clientUser

    companion object : UUIDEntityClass<ClientUserEntity>(ClientUserTable)

    fun toClientUser(): GetClientUser {
        return GetClientUser(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email,
            company = company.toCompany(),
            addresses = addresses.map { addressEntity ->
                addressEntity.toAddress()
            },
            orders = orders.map { orderEntity ->
                orderEntity.toClientOrder()
            }
        )
    }

    fun toCafeUser(): GetCafeClientUser {
        return GetCafeClientUser(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email
        )
    }
}