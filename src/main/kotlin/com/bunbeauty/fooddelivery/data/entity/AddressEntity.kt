package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.table.AddressTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AddressEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var house: String by AddressTable.house
    var flat: String? by AddressTable.flat
    var entrance: String? by AddressTable.entrance
    var floor: String? by AddressTable.floor
    var comment: String? by AddressTable.comment
    var street: StreetEntity by StreetEntity referencedOn AddressTable.street
    var clientUser: ClientUserEntity by ClientUserEntity referencedOn AddressTable.clientUser
    var isVisible: Boolean by AddressTable.isVisible

    val city: CityEntity = street.cafe.city

    companion object : UUIDEntityClass<AddressEntity>(AddressTable)

    fun toAddress() = GetAddress(
        uuid = uuid,
        house = house,
        flat = flat,
        entrance = entrance,
        floor = floor,
        comment = comment,
        street = street.toStreet(),
        userUuid = clientUser.uuid,
        isVisible = isVisible,
    )
}