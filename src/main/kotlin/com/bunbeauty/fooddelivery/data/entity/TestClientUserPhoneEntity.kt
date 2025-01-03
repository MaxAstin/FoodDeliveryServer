package com.bunbeauty.fooddelivery.data.entity

import com.bunbeauty.fooddelivery.data.table.TestClientUserPhoneTable
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetTestClientUserPhone
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class TestClientUserPhoneEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {

    val uuid: String = uuid.value.toString()
    var phoneNumber: String by TestClientUserPhoneTable.phoneNumber
    var code: String by TestClientUserPhoneTable.code

    companion object : UUIDEntityClass<TestClientUserPhoneEntity>(TestClientUserPhoneTable)

    fun toTestClientUserPhone() = GetTestClientUserPhone(
        uuid = uuid,
        phoneNumber = phoneNumber,
        code = code
    )
}
