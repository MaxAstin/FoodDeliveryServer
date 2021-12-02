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
        getAddresses()
        authenticate {
            postAddress()
        }
    }
}

fun Routing.getAddresses() {

    val addressService: IAddressService by inject()

    get("/address") {
        safely {
            val addressList = addressService.getAddressListByUserUuid("")
            call.respondOk(addressList)
        }
    }
}

fun Route.postAddress() {

    val addressService: IAddressService by inject()

    post("/address") {
        managerPost<PostAddress, GetAddress> { jwtUser, postAddress ->
            addressService.createAddress(jwtUser.uuid, postAddress)
        }
    }
}