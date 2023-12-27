package com.bunbeauty.fooddelivery.domain.feature.address

import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.data.features.address.StreetRepository
import com.bunbeauty.fooddelivery.data.features.city.CityRepository
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.noAccessToCompanyError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.*
import com.bunbeauty.fooddelivery.domain.feature.address.model.*
import com.bunbeauty.fooddelivery.domain.toUuid

class AddressService(
    private val addressRepository: AddressRepository,
    private val streetRepository: StreetRepository,
    private val clientUserRepository: ClientUserRepository,
    private val cityRepository: CityRepository,
) {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress {
        val street = streetRepository.getStreetByUuid(streetUuid = postAddress.streetUuid.toUuid())
            .orThrowNotFoundByUuidError(postAddress.streetUuid)
        val clientUser = clientUserRepository.getClientUserByUuid(uuid = userUuid)
            .orThrowNotFoundByUuidError(userUuid)
        if (street.companyUuid != clientUser.company.uuid) {
            noAccessToCompanyError(street.companyUuid)
        }

        val insertAddress = postAddress.mapPostAddress(userUuid)
        return addressRepository.insertAddress(insertAddress = insertAddress)
            .mapAddress()
    }

    suspend fun createAddressV2(userUuid: String, postAddress: PostAddressV2): GetAddressV2 {
        val fiasId = postAddress.street.fiasId
        val suggestion = addressRepository.getSuggestionById(fiasId = fiasId)
            .orThrowNotFoundByUuidError(uuid = fiasId)
        val addressInfo = AddressInfoV2(
            userUuid = userUuid,
            streetLatitude = suggestion.latitude,
            streetLongitude = suggestion.longitude,
        )

        val insertAddress = postAddress.mapPostAddressV2(addressInfo)
        return addressRepository.insertAddressV2(insertAddress = insertAddress)
            .mapAddressV2()
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<GetAddress> {
        return addressRepository.getAddressListByUserUuidAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).map(mapAddress)
    }

    suspend fun getAddressListByUserUuidAndCityUuidV2(userUuid: String, cityUuid: String): List<GetAddressV2> {
        val addressV1List = addressRepository.getAddressListByUserUuidAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).map(mapAddressToV2)
        val addressV2List = addressRepository.getAddressListByUserUuidAndCityUuidV2(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).map(mapAddressV2)

        return addressV1List + addressV2List
    }

    suspend fun getStreetSuggestionList(query: String, cityUuid: String): List<GetSuggestion> {
        val city = cityRepository.getCityByUuid(cityUuid)
            .orThrowNotFoundByUuidError(cityUuid)

        return addressRepository.getStreetSuggestionList(
            query = query,
            city = city
        ).map(mapSuggestion)
    }

}