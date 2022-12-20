package com.bunbeauty.fooddelivery.service.menu_product

import com.bunbeauty.fooddelivery.data.Constants.HITS_COUNT
import com.bunbeauty.fooddelivery.data.Constants.HITS_ORDER_DAY_COUNT
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.menu_product.*
import com.bunbeauty.fooddelivery.data.model.order.client.get.GetClientOrderV2
import com.bunbeauty.fooddelivery.data.repo.category.ICategoryRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.order.IOrderRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.bunbeauty.fooddelivery.service.menu_product.model.HitsCache
import org.joda.time.DateTime

class MenuProductService(
    private val menuProductRepository: IMenuProductRepository,
    private val orderRepository: IOrderRepository,
    private val userRepository: IUserRepository,
    private val categoryRepository: ICategoryRepository,
) : IMenuProductService {

    @Volatile
    private var hitCache: MutableMap<String, HitsCache> = mutableMapOf()

    override suspend fun createMenuProduct(postMenuProduct: PostMenuProduct, creatorUuid: String): GetMenuProduct? {
        val companyUuid = userRepository.getCompanyUuidByUserUuid(creatorUuid.toUuid()) ?: return null
        val insertMenuProduct = InsertMenuProduct(
            name = postMenuProduct.name,
            newPrice = postMenuProduct.newPrice,
            oldPrice = postMenuProduct.oldPrice,
            utils = postMenuProduct.utils,
            nutrition = postMenuProduct.nutrition,
            description = postMenuProduct.description,
            comboDescription = postMenuProduct.comboDescription,
            photoLink = postMenuProduct.photoLink,
            barcode = postMenuProduct.barcode,
            companyUuid = companyUuid.toUuid(),
            categoryUuids = postMenuProduct.categoryUuids.map { categoryUuid ->
                categoryUuid.toUuid()
            },
            isVisible = postMenuProduct.isVisible,
        )

        return menuProductRepository.insertMenuProduct(insertMenuProduct)
    }

    override suspend fun updateMenuProduct(
        menuProductUuid: String,
        patchMenuProduct: PatchMenuProduct,
    ): GetMenuProduct? {
        val updateMenuProduct = UpdateMenuProduct(
            name = patchMenuProduct.name,
            newPrice = patchMenuProduct.newPrice,
            oldPrice = patchMenuProduct.oldPrice,
            utils = patchMenuProduct.utils,
            nutrition = patchMenuProduct.nutrition,
            description = patchMenuProduct.description,
            comboDescription = patchMenuProduct.comboDescription,
            photoLink = patchMenuProduct.photoLink,
            barcode = patchMenuProduct.barcode,
            categoryUuids = patchMenuProduct.categoryUuids?.map { uuid ->
                uuid.toUuid()
            },
            isVisible = patchMenuProduct.isVisible,
        )
        return menuProductRepository.updateMenuProduct(menuProductUuid.toUuid(), updateMenuProduct)
    }

    override suspend fun getMenuProductListByCompanyUuid(companyUuid: String): List<GetMenuProduct> {
        val menuProductList = menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid())
        val limitTime = DateTime.now().withTimeAtStartOfDay().minusDays(HITS_ORDER_DAY_COUNT).millis
        val orderList = orderRepository.getOrderListByCompanyUuidLimited(companyUuid.toUuid(), limitTime)

        val cache = hitCache[companyUuid]
        if (cache == null || !cache.isActual() || cache.hitMenuProductUuidList.size < HITS_COUNT) {
            println("hits calculation")
            hitCache[companyUuid] = HitsCache(
                hitMenuProductUuidList = getHitMenuProductUuidList(orderList, HITS_COUNT),
                dateTime = DateTime.now()
            )
        }
        hitCache[companyUuid]?.let { hitCache ->
            val hitsCategory = categoryRepository.getHitsCategory()
            menuProductList.forEach { menuProduct ->
                if (hitCache.hitMenuProductUuidList.contains(menuProduct.uuid)) {
                    menuProduct.categories.add(hitsCategory)
                }
            }
            println("hits ${hitCache.hitMenuProductUuidList.joinToString()}")
        }

        return menuProductList
    }

    fun getHitMenuProductUuidList(orderList: List<GetClientOrderV2>, count: Int): List<String> {
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