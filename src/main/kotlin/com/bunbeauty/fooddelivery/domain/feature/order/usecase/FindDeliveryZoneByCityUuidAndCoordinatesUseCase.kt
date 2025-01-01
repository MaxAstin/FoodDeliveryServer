package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZoneWithCafe

class FindDeliveryZoneByCityUuidAndCoordinatesUseCase(
    private val cafeRepository: CafeRepository,
    private val checkIsPointInPolygonUseCase: CheckIsPointInPolygonUseCase
) {

    suspend operator fun invoke(
        cityUuid: String,
        latitude: Double,
        longitude: Double,
        addressUuid: String
    ): DeliveryZoneWithCafe {
        val cafeList = cafeRepository.getCafeListByCityUuid(cityUuid = cityUuid)
        return cafeList.filter { cafe ->
            cafe.isVisible
        }.flatMap { cafe ->
            cafe.zones
                .filter { deliveryZone ->
                    deliveryZone.isVisible
                }.map { deliveryZone ->
                    DeliveryZoneWithCafe(
                        cafe = cafe,
                        deliveryZone = deliveryZone
                    )
                }
        }.find { deliveryZoneWithCafe ->
            checkIsPointInPolygonUseCase(
                latitude = latitude,
                longitude = longitude,
                polygon = deliveryZoneWithCafe.deliveryZone.points
                    .sortedBy { point ->
                        point.order
                    }.map { point ->
                        point.latitude to point.longitude
                    }
            )
        } ?: noCafeError(addressUuid = addressUuid)
    }

    private fun noCafeError(addressUuid: String): Nothing {
        error("No cafe associated with address ($addressUuid)")
    }
}
