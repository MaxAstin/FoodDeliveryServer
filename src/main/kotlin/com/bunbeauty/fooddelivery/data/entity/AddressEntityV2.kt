package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.AddressTableV2
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AddressEntityV2(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var streetFiasId: String by AddressTableV2.streetFiasId
    var streetName: String by AddressTableV2.streetName
    var house: String by AddressTableV2.house
    var flat: String? by AddressTableV2.flat
    var entrance: String? by AddressTableV2.entrance
    var floor: String? by AddressTableV2.floor
    var comment: String? by AddressTableV2.comment
    var clientUser: ClientUserEntity by ClientUserEntity referencedOn AddressTableV2.clientUser
    var isVisible: Boolean by AddressTableV2.isVisible

    companion object : UUIDEntityClass<AddressEntityV2>(AddressTableV2)

}