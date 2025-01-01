package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.order.OrderEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.data.table.address.AddressTable
import com.bunbeauty.fooddelivery.data.table.order.OrderTable
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
}
