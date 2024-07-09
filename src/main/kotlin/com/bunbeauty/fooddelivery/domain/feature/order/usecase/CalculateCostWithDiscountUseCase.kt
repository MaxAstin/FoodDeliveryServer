package com.bunbeauty.fooddelivery.domain.feature.order.usecase

class CalculateCostWithDiscountUseCase {

    operator fun invoke(
        cost: Int,
        percentDiscount: Int?,
    ): Int {
        if (percentDiscount == null) {
            return cost
        }

        val discount = (cost * percentDiscount / 100.0).toInt()

        return cost - discount
    }
}