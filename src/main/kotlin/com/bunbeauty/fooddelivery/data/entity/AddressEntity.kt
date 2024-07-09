package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.address.AddressTable
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
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

    companion object : UUIDEntityClass<AddressEntity>(AddressTable)

    @Deprecated(
        message = "Use mapper",
        replaceWith = ReplaceWith("mapAddressEntity")
    )
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