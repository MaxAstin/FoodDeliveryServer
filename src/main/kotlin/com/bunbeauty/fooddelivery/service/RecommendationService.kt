package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.recommendation.GetRecommendationData
import com.bunbeauty.fooddelivery.domain.toUuid

class RecommendationService(private val companyRepository: CompanyRepository) {

    suspend fun getRecommendationDataByCompanyUuid(companyUuid: String): GetRecommendationData {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(companyUuid)
        return GetRecommendationData(
            maxVisibleCount = company.maxVisibleRecommendationCount,
            recommendationList = emptyList(),
        )
    }

}