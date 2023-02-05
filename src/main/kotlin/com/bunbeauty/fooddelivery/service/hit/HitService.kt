package com.bunbeauty.fooddelivery.service.hit

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.Constants.HITS_COUNT
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.hit.IHitRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import org.joda.time.DateTime

class HitService(
    private val companyRepository: ICompanyRepository,
    private val orderRepository: IOrderRepository,
    private val hitRepository: IHitRepository,
) : IHitService {

    override suspend fun updateHits() {
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(Constants.HITS_ORDER_DAY_COUNT).millis
        companyRepository.getCompanyList().forEach { getCompany ->
            val orderList = orderRepository.getOrderListByCompanyUuidLimited(getCompany.uuid.toUuid(), limitTime)
            val hits = getHitMenuProductUuidList(orderList, HITS_COUNT)
            hitRepository.updateHits(getCompany.uuid, hits)
        }
    }

    fun getHitMenuProductUuidList(
        orderList: List<GetClientOrderV2>,
        count: Int
    ): List<String> {
        return orderList.filter { order ->
            order.status == OrderStatus.DELIVERED.name
        }.flatMap { order ->
            order.oderProductList
        }.asSequence()
            .groupBy { orderProduct ->
                orderProduct.menuProduct.uuid
            }.map { (menuProductUuid, orderProductList) ->
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