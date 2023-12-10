package com.bunbeauty.fooddelivery.domain.feature.address

import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.data.features.address.StreetRepository
import com.bunbeauty.fooddelivery.data.features.city.CityRepository
import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.noAccessToCompanyError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapAddress
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapPostAddress
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapSuggestion
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetSuggestion
import com.bunbeauty.fooddelivery.domain.model.address.GetAddress
import com.bunbeauty.fooddelivery.domain.model.address.PostAddress
import com.bunbeauty.fooddelivery.domain.toUuid

class AddressService(
    private val addressRepository: AddressRepository,
    private val streetRepository: StreetRepository,
    private val clientUserRepository: ClientUserRepository,
    private val cityRepository: CityRepository,
) {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress {
        val street = streetRepository.getStreetByUuid(postAddress.streetUuid.toUuid())
            .orThrowNotFoundByUuidError(postAddress.streetUuid)
        val clientUser = clientUserRepository.getClientUserByUuid(userUuid.toUuid())
            .orThrowNotFoundByUuidError(userUuid)
        if (street.companyUuid != clientUser.company.uuid) {
            noAccessToCompanyError(street.companyUuid)
        }

        val insertAddress = postAddress.mapPostAddress(userUuid)
        return addressRepository.insertAddress(insertAddress = insertAddress)
            .mapAddress()
    }

    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<GetAddress> {
        return addressRepository.getAddressListByUserUuidAndCityUuid(
            userUuid = userUuid.toUuid(),
            cityUuid = cityUuid.toUuid()
        ).map(mapAddress)
    }

    suspend fun getStreetSuggestionList(query: String, cityUuid: String): List<GetSuggestion> {
        val city = cityRepository.getCityByUuid(cityUuid.toUuid())
            .orThrowNotFoundByUuidError(cityUuid)

        return addressRepository.getStreetSuggestionList(query, city)
            .map(mapSuggestion)
    }

}