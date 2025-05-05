package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight

object FakeClientUserLight {

    fun create(
        uuid: String = "",
        phoneNumber: String = "",
        email: String = ""
    ): ClientUserLight {
        return ClientUserLight(
            uuid = uuid,
            phoneNumber = phoneNumber,
            email = email
        )
    }
}
