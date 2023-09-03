package com.bunbeauty.fooddelivery.service.hit

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.Constants.HITS_COUNT
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.data.repo.hit.IHitRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.joda.time.DateTime

class HitService(
    private val companyRepository: CompanyRepository,
    private val menuProductRepository: IMenuProductRepository,
    private val orderRepository: IOrderRepository,
    private val hitRepository: IHitRepository,
) : IHitService {

    override suspend fun updateHits() = coroutineScope {
        val monthBeforeMillis = DateTime.now()
            .withTimeAtStartOfDay()
            .minusDays(Constants.HITS_ORDER_DAY_COUNT)
            .millis
        companyRepository.getCompanyList().forEach { company ->
            val orderListAsync = async {
                orderRepository.getOrderListByCompanyUuidLimited(company.uuid.toUuid(), monthBeforeMillis)
            }
            val invisibleMenuProductUuidListAsync = async {
                menuProductRepository.getMenuProductListByCompanyUuid(company.uuid.toUuid()).filter { menuProduct ->
                    !menuProduct.isVisible
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
        orderList: List<GetClientOrderV2>,
        invisibleMenuProductUuidList: List<String>,
        count: Int,
    ): List<String> {
        return orderList.filter { order ->
            order.status == OrderStatus.DELIVERED.name
        }.flatMap { order ->
            order.oderProductList
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