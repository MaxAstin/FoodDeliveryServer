package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.model.address.PostAddress
import com.bunbeauty.fooddelivery.routing.extension.client
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.service.address.IAddressService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureAddressRouting() {

    routing {
        authenticate {
            getAddresses()
            postAddress()
        }
    }
}

fun Route.getAddresses() {

    val addressService: IAddressService by inject()

    get("/address") {
        client { request ->
            val addressList = addressService.getAddressListByUserUuid(request.jwtUser.uuid)
            call.respondOk(addressList)
        }
    }
}

fun Route.postAddress() {

    val addressService: IAddressService by inject()

    post("/address") {
        clientWithBody<PostAddress, GetAddress> { bodyRequest ->
            addressService.createAddress(bodyRequest.request.jwtUser.uuid, bodyRequest.body)
        }
    }
}