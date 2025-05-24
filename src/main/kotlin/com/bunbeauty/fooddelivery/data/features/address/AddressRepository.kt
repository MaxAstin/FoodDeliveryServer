package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.AddressEntity
import com.bunbeauty.fooddelivery.data.entity.AddressEntityV2
import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.StreetEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntityV2
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapSuggestionsResponse
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressByIdRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.Bound
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.Location
import com.bunbeauty.fooddelivery.data.table.address.AddressTable
import com.bunbeauty.fooddelivery.data.table.address.AddressV2Table
import com.bunbeauty.fooddelivery.domain.feature.address.model.Address
import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.InsertAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.InsertAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.Suggestion
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.network.getDataOrNull
import org.jetbrains.exposed.sql.and

private const val STREET_BOUND = "street"
private const val VILLAGE_BOUND = "settlement"

class AddressRepository(
    private val addressNetworkDataSource: AddressNetworkDataSource
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
                streetFiasId = insertAddress.street.fiasId
                streetName = insertAddress.street.name
                streetLatitude = insertAddress.street.latitude
                streetLongitude = insertAddress.street.longitude
                house = insertAddress.house
                flat = insertAddress.flat
                entrance = insertAddress.entrance
                floor = insertAddress.floor
                comment = insertAddress.comment
                isVisible = insertAddress.isVisible
                clientUser = ClientUserEntity[insertAddress.clientUserUuid.toUuid()]
                city = CityEntity[insertAddress.cityUuid.toUuid()]
                deliveryZoneUuid = insertAddress.deliveryZoneUuid
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

    suspend fun getAddressListByUserUuidAndCityUuidV2(userUuid: String, cityUuid: String): List<AddressV2> {
        return query {
            AddressEntityV2.find {
                (AddressV2Table.clientUser eq userUuid.toUuid()) and
                    (AddressV2Table.city eq cityUuid.toUuid())
            }.toList()
                .map(mapAddressEntityV2)
        }
    }

    suspend fun getAddressByUuid(uuid: String): Address? {
        return query {
            AddressEntity.findById(uuid.toUuid())?.mapAddressEntity()
        }
    }

    suspend fun getAddressV2ByUuid(uuid: String): AddressV2? {
        return query {
            AddressEntityV2.findById(uuid.toUuid())?.mapAddressEntityV2()
        }
    }

    suspend fun getSuggestionById(fiasId: String): Suggestion? {
        return addressNetworkDataSource.requestAddressSuggestionById(
            AddressByIdRequestBody(query = fiasId)
        ).getDataOrNull()
            ?.mapSuggestionsResponse()
            ?.firstOrNull()
    }

    suspend fun getStreetSuggestionList(query: String, city: City): List<Suggestion> {
        return addressNetworkDataSource.requestAddressSuggestions(
            AddressRequestBody(
                query = query,
                fromBound = Bound(value = STREET_BOUND),
                toBound = Bound(value = VILLAGE_BOUND),
                locations = listOf(Location(city = city.name))
            )
        ).getDataOrNull()
            ?.mapSuggestionsResponse()
            .orEmpty()
    }
}
