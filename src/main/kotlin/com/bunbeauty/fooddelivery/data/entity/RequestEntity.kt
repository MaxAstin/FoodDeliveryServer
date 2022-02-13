package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.model.request.GetRequest
import com.bunbeauty.fooddelivery.data.table.RequestTable
import com.bunbeauty.fooddelivery.data.table.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class RequestEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var ip: String by RequestTable.ip
    var name: String by RequestTable.name
    var time: Long by RequestTable.time

    companion object : UUIDEntityClass<RequestEntity>(RequestTable)

    fun toRequest(): GetRequest {
        return GetRequest(
            ip = ip,
            name = name,
            time = time
        )
    }
}