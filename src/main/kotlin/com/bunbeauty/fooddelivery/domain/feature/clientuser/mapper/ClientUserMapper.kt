package com.bunbeauty.fooddelivery.domain.feature.clientuser.mapper

import com.bunbeauty.fooddelivery.domain.feature.address.mapper.mapAddress
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUser
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserWithOrders
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapCompanyWithCafes
import com.bunbeauty.fooddelivery.domain.feature.order.mapper.mapOrder
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal
import com.bunbeauty.fooddelivery.domain.model.client_user.GetCafeClientUser
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientSettings
import com.bunbeauty.fooddelivery.domain.model.client_user.GetClientUser

val mapClientUserLight: ClientUserLight.() -> GetCafeClientUser = {
    GetCafeClientUser(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email
    )
}

val mapClientUserWithOrders: ClientUserWithOrders.() -> GetClientUser = {
    GetClientUser(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
        company = companyWithCafes.mapCompanyWithCafes(),
        addresses = addresses.map(mapAddress),
        orders = orders.map { order ->
            order.mapOrder(
                OrderTotal(
                    oldTotalCost = null,
                    newTotalCost = 0,
                    productTotalMap = emptyMap()
                )
            )
        }
    )
}

val mapClientUserWithCafesToClientSettingsWithOrders: ClientUserWithOrders.() -> GetClientSettings = {
    GetClientSettings(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
        isActive = isActive
    )
}

val mapClientUserToClientSettingsWithOrders: ClientUser.() -> GetClientSettings = {
    GetClientSettings(
        uuid = uuid,
        phoneNumber = phoneNumber,
        email = email,
        isActive = isActive
    )
}
