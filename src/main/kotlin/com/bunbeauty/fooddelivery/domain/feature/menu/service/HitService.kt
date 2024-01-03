package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.Constants.HITS_COUNT
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.joda.time.DateTime

class HitService(
    private val companyRepository: CompanyRepository,
    private val menuProductRepository: MenuProductRepository,
    private val orderRepository: OrderRepository,
    private val hitRepository: HitRepository,
) {

    suspend fun updateHits() = coroutineScope {
        val monthBeforeMillis = DateTime.now()
            .withTimeAtStartOfDay()
            .minusDays(Constants.HITS_ORDER_DAY_COUNT)
            .millis
        companyRepository.getCompanyList().forEach { company ->
            val orderListAsync = async {
                orderRepository.getOrderListByCompanyUuidLimited(
                    companyUuid = company.uuid,
                    limitTime = monthBeforeMillis
                )
            }
            val invisibleMenuProductUuidListAsync = async {
                menuProductRepository.getMenuProductListByCompanyUuid(
                    companyUuid = company.uuid
                ).filterNot { menuProduct ->
                    menuProduct.isVisible
                }.map { menuProduct ->
                    menuProduct.uuid
                }
            }
            val hits = getHitMenuProductUuidList(
                orderList = orderListAsync.await(),
                invisibleMenuProductUuidList = invisibleMenuProductUuidListAsync.await(),
                count = HITS_COUNT
            )
            println("updateHits ${company.name} hits ${hits.joinToString()}")
            hitRepository.updateHits(company.uuid, hits)
        }
    }

    fun getHitMenuProductUuidList(
        orderList: List<Order>,
        invisibleMenuProductUuidList: List<String>,
        count: Int,
    ): List<String> {
        return orderList.filter { order ->
            order.status == OrderStatus.DELIVERED.name
        }.flatMap { order ->
            order.oderProducts
        }.asSequence()
            .filter { orderProduct ->
                !invisibleMenuProductUuidList.contains(orderProduct.menuProduct.uuid)
            }
            .groupBy { orderProduct ->
                orderProduct.menuProduct.uuid
            }
            .map { (menuProductUuid, orderProductList) ->
                menuProductUuid to orderProductList.sumOf { orderProduct ->
                    orderProduct.count * orderProduct.newPrice
                }
            }.sortedByDescending { (_, cost) ->
                cost
            }.take(count)
            .map { (menuProductUuid, _) ->
                menuProductUuid
            }.toList()
    }
}