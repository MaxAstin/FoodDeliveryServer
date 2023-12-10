package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import java.util.*

class AddressDao {

    suspend fun insertAddress(insertAddress: InsertAddress): AddressEntity = query {
        AddressEntity.new {
            house = insertAddress.house
            flat = insertAddress.flat
            entrance = insertAddress.entrance
            floor = insertAddress.floor
            comment = insertAddress.comment
            street = StreetEntity[insertAddress.streetUuid]
            clientUser = ClientUserEntity[insertAddress.clientUserUuid]
            isVisible = insertAddress.isVisible
        }
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<AddressEntity> = query {
        AddressEntity.find {
            AddressTable.clientUser eq userUuid
        }.filter {addressEntity ->
            addressEntity.street.cafe.city.id.value == cityUuid
        }.toList()
    }

}