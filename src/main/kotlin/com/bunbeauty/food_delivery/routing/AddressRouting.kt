package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.address.PostAddress
import com.bunbeauty.food_delivery.service.address.IAddressService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
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