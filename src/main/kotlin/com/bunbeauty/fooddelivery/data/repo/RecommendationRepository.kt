package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.RecommendationEntity
import com.bunbeauty.fooddelivery.data.model.recommendation.GetRecommendation
import com.bunbeauty.fooddelivery.data.model.recommendation.InsertRecommendation
import com.bunbeauty.fooddelivery.data.table.RecommendationTable
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