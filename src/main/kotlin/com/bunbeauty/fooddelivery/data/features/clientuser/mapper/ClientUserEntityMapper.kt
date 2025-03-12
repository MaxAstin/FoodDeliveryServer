package com.bunbeauty.fooddelivery.data.features.clientuser.mapper

import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.features.address.mapper.mapAddressEntity
import com.bunbeauty.fooddelivery.data.features.company.mapper.mapCompanyWithCafesEntity
import com.bunbeauty.fooddelivery.data.features.order.mapper.mapOrderEntity
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserWithOrders
import org.jetbrains.exposed.sql.ResultRow

val mapClientUserEntityToLight: ClientUserEntity.() -> ClientUserLight = {
    ClientUserLight(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email
    )
}

val mapClientUserWithOrdersEntity: ClientUserEntity.() -> ClientUserWithOrders = {
    ClientUserWithOrders(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
        isActive = isActive,
        companyWithCafes = company.mapCompanyWithCafesEntity(),
        addresses = addresses.map(mapAddressEntity),
        orders = orders.map(mapOrderEntity)
    )
}

val mapClientUserEntity: ResultRow.() -> ClientUser = {
    ClientUser(
        uuid = this[ClientUserTable.id].value.toString(),
        phoneNumber = this[ClientUserTable.phoneNumber],
        email = this[ClientUserTable.email],
        isActive = this[ClientUserTable.isActive]
    )
}
