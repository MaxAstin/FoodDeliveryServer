package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.InsertRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.PostRecommendation
import com.bunbeauty.fooddelivery.data.repo.RecommendationRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.MenuProductRepository

class RecommendationService(
    private val recommendationRepository: RecommendationRepository,
    private val menuProductRepository: MenuProductRepository,
) {

    suspend fun getRecommendationListByCompanyUuid(companyUuid: String): List<GetRecommendation> {
        val menuProductList = menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid())
        return menuProductList.mapNotNull { menuProduct ->
            recommendationRepository.getRecommendationByMenuProductUuid(menuProduct.uuid.toUuid())
        }
    }

    suspend fun createRecommendation(postRecommendation: PostRecommendation): GetRecommendation {
        return recommendationRepository.insertRecommendation(
            insertRecommendation = InsertRecommendation(
                menuProductUuid = postRecommendation.menuProductUuid.toUuid()
            )
        )
    }

}