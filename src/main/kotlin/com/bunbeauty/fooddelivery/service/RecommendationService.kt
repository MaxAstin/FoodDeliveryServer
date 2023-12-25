package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.data.repo.RecommendationRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.domain.model.recommendation.GetRecommendationData
import com.bunbeauty.fooddelivery.domain.model.recommendation.InsertRecommendation
import com.bunbeauty.fooddelivery.domain.model.recommendation.PostRecommendation
import com.bunbeauty.fooddelivery.domain.toUuid

class RecommendationService(
    private val companyRepository: CompanyRepository,
    private val recommendationRepository: RecommendationRepository,
    private val menuProductRepository: MenuProductRepository,
) {

    suspend fun getRecommendationDataByCompanyUuid(companyUuid: String): GetRecommendationData {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(companyUuid)
        val menuProductList = menuProductRepository.getMenuProductListByCompanyUuid(companyUuid = companyUuid)
        val recommendationList = menuProductList.mapNotNull { menuProduct ->
            recommendationRepository.getRecommendationByMenuProductUuid(
                menuProductUuid = menuProduct.uuid.toUuid()
            )?.let { recommendation ->
                recommendation.copy(isVisible = recommendation.isVisible && menuProduct.isVisible)
            }
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