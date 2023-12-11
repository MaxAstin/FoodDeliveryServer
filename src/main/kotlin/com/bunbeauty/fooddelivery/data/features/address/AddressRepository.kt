package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationNetworkDataSource
import com.bunbeauty.fooddelivery.data.table.AddressTable
import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.address.model.Suggestion
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import com.bunbeauty.fooddelivery.domain.toUuid

private const val STREET_BOUND = "street"

class AddressRepository(
    private val addressNetworkDataSource: AddressNetworkDataSource,
    private val authorizationNetworkDataSource: AuthorizationNetworkDataSource,
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
//        return addressNetworkDataSource.requestAddressSuggestions(
//            AddressRequestBody(
//                query = query,
//                fromBound = STREET_BOUND,
//                toBound = STREET_BOUND,
//                locations = city.name,
//            )
//        ).getDataOrNull()
//            ?.mapSuggestionsResponse()
//            .orEmpty()

        authorizationNetworkDataSource.checkBalance()

        return emptyList()
    }

}