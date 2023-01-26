package com.bunbeauty.fooddelivery.data.repo.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.model.address.InsertAddress
import com.bunbeauty.fooddelivery.data.table.AddressTable
import java.util.*

class AddressRepository : IAddressRepository {

    override suspend fun insertAddress(insertAddress: InsertAddress): GetAddress = query {
        AddressEntity.new {
            house = insertAddress.house
            flat = insertAddress.flat
            entrance = insertAddress.entrance
            floor = insertAddress.floor
            comment = insertAddress.comment
            street = StreetEntity[insertAddress.streetUuid]
            clientUser = ClientUserEntity[insertAddress.clientUserUuid]
            isVisible = insertAddress.isVisible
        }.toAddress()
    }

    override suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<GetAddress> = query {
        AddressEntity.find {
            AddressTable.clientUser eq userUuid
        }.filter { addressEntity ->
            addressEntity.street.cafe.city.id.value == cityUuid
        }.map { addressEntity ->
            addressEntity.toAddress()
        }
    }
}