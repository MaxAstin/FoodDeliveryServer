package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.AddressEntityV2
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntityV2
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapSuggestionsResponse
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.Bound
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.Location
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.domain.feature.address.model.*
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.network.getDataOrNull

private const val STREET_BOUND = "street"

class AddressRepository(
    private val addressNetworkDataSource: AddressNetworkDataSource,
) {

    suspend fun insertAddress(insertAddress: InsertAddress): Address {
        return query {
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
    }

    suspend fun insertAddressV2(insertAddress: InsertAddressV2): AddressV2 {
        return query {
            AddressEntityV2.new {
                streetFiasId = insertAddress.streetFiasId
                streetName = insertAddress.streetName
                house = insertAddress.house
                flat = insertAddress.flat
                entrance = insertAddress.entrance
                floor = insertAddress.floor
                comment = insertAddress.comment
                clientUser = ClientUserEntity[insertAddress.clientUserUuid]
                isVisible = insertAddress.isVisible
            }.mapAddressEntityV2()
        }
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<Address> {
        return query {
            AddressEntity.find {
                AddressTable.clientUser eq userUuid.toUuid()
            }.filter { addressEntity ->
                addressEntity.street.cafe.city.uuid == cityUuid
            }.toList()
                .map(mapAddressEntity)
        }
    }

    suspend fun getStreetSuggestionList(query: String, city: City): List<Suggestion> {
        return addressNetworkDataSource.requestAddressSuggestions(
            AddressRequestBody(
                query = query,
                fromBound = Bound(value = STREET_BOUND),
                toBound = Bound(value = STREET_BOUND),
                locations = listOf(Location(city = city.name)),
            )
        ).getDataOrNull()
            ?.mapSuggestionsResponse()
            .orEmpty()
    }

}