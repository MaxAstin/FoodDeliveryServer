package com.bunbeauty.fooddelivery.domain.feature.privacy

import com.bunbeauty.fooddelivery.data.entity.CityEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.isNotAvailableForYourCompanyError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.toUuid

class PrivacyCheckService(
    private val userRepository: UserRepository,
) {

    suspend fun checkIsCityAvailable(userUuid: String, cityUuid: String) {
        val user = userRepository.getUserByUuid(userUuid.toUuid()).orThrowNotFoundByUuidError(userUuid)
        val isAvailable = user.company.cities.any { city ->
            city.uuid == cityUuid
        }
        if (!isAvailable) {
            isNotAvailableForYourCompanyError(
                entity = CityEntity::class,
                uuid = cityUuid,
                companyUuid = user.companyUuid
            )
        }
    }

    suspend fun checkIsCafeAvailable(userUuid: String, cafeUuid: String) {
        val user = userRepository.getUserByUuid(userUuid.toUuid()).orThrowNotFoundByUuidError(userUuid)
        val isAvailable = user.company.cities.flatMap { city ->
            city.cafes
        }.any { cafe ->
            cafe.uuid == cafeUuid
        }
        if (!isAvailable) {
            isNotAvailableForYourCompanyError(
                entity = CafeEntity::class,
                uuid = cafeUuid,
                companyUuid = user.companyUuid
            )
        }
    }

}