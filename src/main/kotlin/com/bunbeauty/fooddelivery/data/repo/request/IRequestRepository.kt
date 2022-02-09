package com.bunbeauty.fooddelivery.data.repo.request

import com.bunbeauty.fooddelivery.data.model.request.GetRequest
import com.bunbeauty.fooddelivery.data.model.request.InsertRequest

interface IRequestRepository {

    suspend fun getLastDayRequestByIpAndName(ip: String, name: String, startDayMillis: Long): GetRequest?
    suspend fun getDayRequestCountByIpAndName(ip: String, name: String, startDayMillis: Long): Long
    suspend fun insertRequest(insertRequest: InsertRequest): GetRequest
}