package com.bunbeauty.fooddelivery.domain.feature.cafe

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.toUuid

class GetCafeByUserAddressUseCase(
    private val cafeRepository: CafeRepository
) {
    suspend operator fun invoke(userAddressUuid: String): Cafe {
        val cafe = cafeRepository.getCafeByUserAddressUuid(uuid = userAddressUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = userAddressUuid)

        return cafe
    }
}
