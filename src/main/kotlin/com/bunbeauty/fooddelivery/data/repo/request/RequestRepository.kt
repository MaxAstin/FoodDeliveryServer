package com.bunbeauty.fooddelivery.data.repo.request

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.RequestEntity
import com.bunbeauty.fooddelivery.data.table.RequestTable
import com.bunbeauty.fooddelivery.domain.model.request.GetRequest
import com.bunbeauty.fooddelivery.domain.model.request.InsertRequest
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and

class RequestRepository : IRequestRepository {

    override suspend fun getLastRequestByIpAndName(ip: String, name: String): GetRequest? =
        query {
            RequestEntity.find {
                (RequestTable.ip eq ip) and
                        (RequestTable.name eq name)
            }.orderBy(RequestTable.time to SortOrder.DESC)
                .firstOrNull()
                ?.toRequest()
        }

    override suspend fun getRequestCountByIpAndName(ip: String, name: String): Long = query {
        RequestEntity.find {
            (RequestTable.ip eq ip) and
                    (RequestTable.name eq name)
        }.count()
    }

    override suspend fun insertRequest(insertRequest: InsertRequest): GetRequest = query {
        RequestEntity.new {
            ip = insertRequest.ip
            name = insertRequest.name
            time = insertRequest.time
        }.toRequest()
    }

    override suspend fun deleteAll() = query {
        RequestEntity.all().forEach { requestEntity ->
            requestEntity.delete()
        }
    }
}