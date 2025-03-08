package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.address.AddressV2Table
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class AddressEntityV2(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var streetFiasId: String by AddressV2Table.streetFiasId
    var streetName: String by AddressV2Table.streetName
    var streetLatitude: Double by AddressV2Table.streetLatitude
    var streetLongitude: Double by AddressV2Table.streetLongitude
    var house: String by AddressV2Table.house
    var flat: String? by AddressV2Table.flat
    var entrance: String? by AddressV2Table.entrance
    var floor: String? by AddressV2Table.floor
    var comment: String? by AddressV2Table.comment
    var isVisible: Boolean by AddressV2Table.isVisible
    var deliveryZoneUuid: String? by AddressV2Table.deliveryZoneUuid

    var clientUser: ClientUserEntity by ClientUserEntity referencedOn AddressV2Table.clientUser

    @Deprecated("Not need city because we have cafe uuid")
    var city: CityEntity by CityEntity referencedOn AddressV2Table.city

    companion object : UUIDEntityClass<AddressEntityV2>(AddressV2Table)
}
