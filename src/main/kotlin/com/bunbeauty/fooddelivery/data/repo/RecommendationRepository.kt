package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.RecommendationEntity
import com.bunbeauty.fooddelivery.data.table.menu.RecommendationTable
import com.bunbeauty.fooddelivery.domain.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.domain.model.recommendation.InsertRecommendation
import java.util.*

class RecommendationRepository {

    suspend fun getRecommendationByMenuProductUuid(menuProductUuid: UUID): GetRecommendation? = query {
        RecommendationEntity.find {
            RecommendationTable.menuProduct eq menuProductUuid
        }.singleOrNull()?.toRecommendation()
    }

    suspend fun insertRecommendation(insertRecommendation: InsertRecommendation): GetRecommendation = query {
        RecommendationEntity.new {
            menuProduct = MenuProductEntity[insertRecommendation.menuProductUuid]
        }.toRecommendation()
    }

}