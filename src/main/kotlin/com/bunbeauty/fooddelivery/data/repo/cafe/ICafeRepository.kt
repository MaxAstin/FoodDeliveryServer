package com.bunbeauty.fooddelivery.data.repo.cafe

import com.bunbeauty.fooddelivery.data.model.cafe.GetCafe
import com.bunbeauty.fooddelivery.data.model.cafe.InsertCafe
import java.util.UUID

interface ICafeRepository {

    suspend fun insertCafe(insertCafe: InsertCafe): GetCafe
    suspend fun getCafeListByCityUuid(cityUuid: UUID): List<GetCafe>
    suspend fun getCafeListByCompanyUuid(companyUuid: UUID): List<GetCafe>
    suspend fun incrementCafeCodeCounter(cafeUuid: UUID, divisor: Int): Int?
}