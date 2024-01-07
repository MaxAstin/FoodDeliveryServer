package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal
import com.bunbeauty.fooddelivery.util.orZero

class CalculateOrderTotalUseCase {

    operator fun invoke(order: Order): OrderTotal {
        return OrderTotal(
            oldTotalCost = order.oldTotalCost,
            newTotalCost = order.newTotalCost,
            productTotalMap = order.oderProducts.associate { orderProduct ->
                orderProduct.total
            }
        )
    }

    private val Order.newTotalCost: Int
        get() {
            val oderProductsSumCost = oderProducts.sumOf { orderProduct ->
                orderProduct.newTotalCost
            }
            val discount = (oderProductsSumCost * (percentDiscount ?: 0) / 100.0).toInt()

            return oderProductsSumCost - discount + (deliveryCost ?: 0)
        }

    private val Order.oldTotalCost: Int?
        get() {
            val isOldTotalCostEnabled = oderProducts.any { orderProduct ->
                orderProduct.oldPrice != null
            } || percentDiscount != null
            return if (isOldTotalCostEnabled) {
                oderProducts.sumOf { orderProduct ->
                    orderProduct.oldTotalCost ?: orderProduct.newTotalCost
                } + (deliveryCost ?: 0)
            } else {
                null
            }
        }

    private val OrderProduct.total: Pair<String, OrderProductTotal>
        get() {
            return uuid to OrderProductTotal(
                additionsPrice = additionsPrice,
                newCommonPrice = newCommonPrice,
                oldCommonPrice = oldCommonPrice,
                oldTotalCost = oldTotalCost,
                newTotalCost = newTotalCost,
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