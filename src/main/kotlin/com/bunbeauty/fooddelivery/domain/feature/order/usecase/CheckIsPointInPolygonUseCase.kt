package com.bunbeauty.fooddelivery.domain.feature.order.usecase

class CheckIsPointInPolygonUseCase {

   operator fun invoke(
        latitude: Double,
        longitude: Double,
        polygon: List<Pair<Double, Double>>
    ): Boolean {
        var inside = false

        for (i in polygon.indices) {
            val (xi, yi) = polygon[i]
            val j = (i + 1) % polygon.size
            val (xj, yj) = polygon[j]

            if ((yi == longitude && ((latitude in xi..xj) || (latitude in xj..xi))) ||
                ((yi > longitude) != (yj > longitude)) && (latitude < (xj - xi) * (longitude - yi) / (yj - yi) + xi)
            ) {
                inside = !inside
            }
        }

        return inside
    }
}