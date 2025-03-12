package com.bunbeauty.fooddelivery.domain.feature.address

import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.data.features.address.StreetRepository
import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.features.city.CityRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.noAccessToCompanyError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapAddress
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapAddressToV2
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapPostAddress
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapPostAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapSuggestion
import com.bunbeauty.fooddelivery.domain.feature.address.model.AddressInfoV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetSuggestion
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddressV2
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.order.usecase.CheckIsPointInPolygonUseCase
import com.bunbeauty.fooddelivery.domain.toUuid

class AddressService(
    private val addressRepository: AddressRepository,
    private val streetRepository: StreetRepository,
    private val companyRepository: CompanyRepository,
    private val cityRepository: CityRepository,
    private val cafeRepository: CafeRepository,
    private val checkIsPointInPolygonUseCase: CheckIsPointInPolygonUseCase
) {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress {
        val street = streetRepository.getStreetByUuid(streetUuid = postAddress.streetUuid.toUuid())
            .orThrowNotFoundByUuidError(postAddress.streetUuid)
        val company = companyRepository.getCompanyByUserUuid(userUuid = userUuid)
            .orThrowNotFoundByUuidError(userUuid)
        if (street.companyUuid != company.uuid) {
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
            streetLongitude = suggestion.longitude
        )
        val deliveryZone = getDeliveryZoneByCoordinates(
            cityUuid = postAddress.cityUuid,
            latitude = suggestion.latitude,
            longitude = suggestion.longitude
        ) ?: deliveryNotAvailableAtThisAddress(suggestion.latitude, suggestion.longitude)
        val insertAddress = postAddress.mapPostAddressV2(addressInfo, deliveryZone.uuid)
        return addressRepository.insertAddressV2(insertAddress = insertAddress)
            .mapAddressV2(deliveryZone)
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
        ).mapNotNull { address ->
            getDeliveryZoneByCoordinates(
                cityUuid = cityUuid,
                latitude = address.street.latitude,
                longitude = address.street.longitude
            )?.let { deliveryZone ->
                address.mapAddressToV2(deliveryZone)
            }
        }
        val addressV2List = addressRepository.getAddressListByUserUuidAndCityUuidV2(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).mapNotNull { address ->
            getDeliveryZoneByCoordinates(
                cityUuid = cityUuid,
                latitude = address.street.latitude,
                longitude = address.street.longitude
            )?.let { deliveryZone ->
                address.mapAddressV2(deliveryZone)
            }
        }

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

    private suspend fun getDeliveryZoneByCoordinates(
        cityUuid: String,
        latitude: Double,
        longitude: Double
    ): DeliveryZone? {
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid = cityUuid)
        return cafeList
            .asSequence()
            .filter { cafe -> cafe.isVisible }
            .map { cafe ->
                cafe.zones
            }.flatten()
            .filter { zone -> zone.isVisible }
            .find { zone ->
                checkIsPointInPolygonUseCase(
                    latitude = latitude,
                    longitude = longitude,
                    polygon = zone.points.sortedBy { point ->
                        point.order
                    }.map { point ->
                        point.latitude to point.longitude
                    }
                )
            }
    }

    /*
     * TODO(Remove in 1.0.6 release after add all cafeUuid to address)
     * */
    suspend fun addToAddressDeliveryZoneUuid(cityUuid: String, newDeliveryZoneUuid: String) {
        val addressList = addressRepository.getAddressListByCityUuidV2(cityUuid)
        addressList.forEach { address ->
            addressRepository.patchAddressDeliveryZoneUuid(
                addressUuid = address.uuid,
                newDeliveryZoneUuid = newDeliveryZoneUuid
            )
        }
    }

    private fun deliveryNotAvailableAtThisAddress(latitude: Double, longitude: Double): Nothing {
        error("Delivery not available at this address: $latitude,$longitude")
    }
}
