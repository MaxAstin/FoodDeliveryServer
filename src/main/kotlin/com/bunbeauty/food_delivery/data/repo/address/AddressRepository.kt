package com.bunbeauty.food_delivery.data.repo.address

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.AddressEntity
import com.bunbeauty.food_delivery.data.entity.StreetEntity
import com.bunbeauty.food_delivery.data.entity.UserEntity
import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.address.InsertAddress
import com.bunbeauty.food_delivery.data.table.AddressTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.booleanLiteral
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
            user = UserEntity[insertAddress.userUuid]
            isVisible = insertAddress.isVisible
        }.toAddress()
    }

    override suspend fun getAddressListByUserUuid(uuid: UUID): List<GetAddress> {
        return AddressEntity.find {
            AddressTable.user eq uuid and
                    AddressTable.isVisible eq booleanLiteral(true)
        }.map { addressEntity ->
            addressEntity.toAddress()
        }
    }
}