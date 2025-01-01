package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal
import com.bunbeauty.fooddelivery.util.orZero

class CalculateOrderProductTotalUseCase {

    operator fun invoke(orderProduct: OrderProduct): OrderProductTotal {
        return OrderProductTotal(
            additionsPrice = orderProduct.additionsPrice,
            newCommonPrice = orderProduct.newCommonPrice,
            oldCommonPrice = orderProduct.oldCommonPrice,
            oldTotalCost = orderProduct.oldTotalCost,
            newTotalCost = orderProduct.newTotalCost,
        )
    }

    private val OrderProduct.newTotalCost: Int
        get() {
            return count * newCommonPrice
        }

    private val OrderProduct.oldTotalCost: Int?
        get() {
            return oldCommonPrice?.let { price ->
                count * price
            }
        }

    private val OrderProduct.newCommonPrice: Int
        get() {
            return newPrice + additionsPrice.orZero()
        }

    private val OrderProduct.oldCommonPrice: Int?
        get() {
            return oldPrice?.let {
                oldPrice + additionsPrice.orZero()
            }
        }

    private val OrderProduct.additionsPrice: Int?
        get() {
            val noAdditionsWithPrice = additions.all { addition ->
                addition.price == null
            }
            return if (noAdditionsWithPrice) {
                null
            } else {
                additions.sumOf { addition ->
                    addition.price ?: 0
                }
            }
        }

}