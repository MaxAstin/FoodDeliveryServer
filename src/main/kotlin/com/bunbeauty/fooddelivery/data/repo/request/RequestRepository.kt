package com.bunbeauty.fooddelivery.data.repo.request

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.RequestEntity
import com.bunbeauty.fooddelivery.data.model.request.GetRequest
import com.bunbeauty.fooddelivery.data.model.request.InsertRequest
import com.bunbeauty.fooddelivery.data.table.RequestTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and

class RequestRepository : IRequestRepository {

    override suspend fun getLastDayRequestByIpAndName(ip: String, name: String, startDayMillis: Long): GetRequest? =
        query {
            RequestEntity.find {
                (RequestTable.ip eq ip) and
                        (RequestTable.name eq name) and
                        (RequestTable.time greater startDayMillis)
            }.orderBy(RequestTable.time to SortOrder.DESC)
                .firstOrNull()
                ?.toRequest()
        }

    override suspend fun getDayRequestCountByIpAndName(ip: String, name: String, startDayMillis: Long): Long = query {
        RequestEntity.find {
            (RequestTable.ip eq ip) and
                    (RequestTable.name eq name) and
                    (RequestTable.time greater startDayMillis)
        }.count()
    }

    override suspend fun insertRequest(insertRequest: InsertRequest): GetRequest = query {
        RequestEntity.new {
            ip = insertRequest.ip
            name = insertRequest.name
            time = insertRequest.time
        }.toRequest()
    }
}