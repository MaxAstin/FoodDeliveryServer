package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.GetRecommendationData
import com.bunbeauty.fooddelivery.data.model.recommendation.InsertRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.PostRecommendation
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.data.repo.RecommendationRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.MenuProductRepository
import com.bunbeauty.fooddelivery.error.orThrowNotFoundByUuidError

class RecommendationService(
    private val companyRepository: CompanyRepository,
    private val recommendationRepository: RecommendationRepository,
    private val menuProductRepository: MenuProductRepository,
) {

    suspend fun getRecommendationDataByCompanyUuid(companyUuid: String): GetRecommendationData {
        val company = companyRepository.getCompanyByUuid(companyUuid.toUuid()).orThrowNotFoundByUuidError(companyUuid)
        val menuProductList = menuProductRepository.getMenuProductListByCompanyUuid(companyUuid.toUuid())
        val recommendationList = menuProductList.mapNotNull { menuProduct ->
            recommendationRepository.getRecommendationByMenuProductUuid(menuProduct.uuid.toUuid())
        }
        return GetRecommendationData(
            maxVisibleCount = company.maxVisibleRecommendationCount,
            recommendationList = recommendationList,
        )
    }

    suspend fun createRecommendation(postRecommendation: PostRecommendation): GetRecommendation {
        return recommendationRepository.insertRecommendation(
            insertRecommendation = InsertRecommendation(
                menuProductUuid = postRecommendation.menuProductUuid.toUuid()
            )
        )
    }

}