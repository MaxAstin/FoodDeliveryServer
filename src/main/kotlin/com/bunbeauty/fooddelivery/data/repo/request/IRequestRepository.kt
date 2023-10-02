package com.bunbeauty.fooddelivery.data.repo.request

import com.bunbeauty.fooddelivery.data.model.request.GetRequest
import com.bunbeauty.fooddelivery.data.model.request.InsertRequest

interface IRequestRepository {

    suspend fun getLastRequestByIpAndName(ip: String, name: String): GetRequest?
    suspend fun getRequestCountByIpAndName(ip: String, name: String): Long
    suspend fun insertRequest(insertRequest: InsertRequest): GetRequest
    suspend fun deleteAll()
}