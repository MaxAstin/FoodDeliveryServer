package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import java.util.*

// TODO move back to Repo
class AddressDao {

    suspend fun insertAddress(insertAddress: InsertAddress): Address = query {
        AddressEntity.new {
            house = insertAddress.house
            flat = insertAddress.flat
            entrance = insertAddress.entrance
            floor = insertAddress.floor
            comment = insertAddress.comment
            street = StreetEntity[insertAddress.streetUuid]
            clientUser = ClientUserEntity[insertAddress.clientUserUuid]
            isVisible = insertAddress.isVisible
        }.mapAddressEntity()
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<Address> = query {
        AddressEntity.find {
            AddressTable.clientUser eq userUuid
        }.filter { addressEntity ->
            addressEntity.street.cafe.city.id.value == cityUuid
        }.toList()
            .map(mapAddressEntity)
    }

}