package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
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

private fun Route.getAddresses() {

    val addressService: IAddressService by inject()

    get("/address") {
        client { request ->
            val cityUuid = call.parameters[Constants.CITY_UUID_PARAMETER] ?: error("${Constants.CITY_UUID_PARAMETER} is required")
            val addressList = addressService.getAddressListByUserUuidAndCityUuid(request.jwtUser.uuid, cityUuid)
            call.respondOk(addressList)
        }
    }
}

private fun Route.postAddress() {

    val addressService: IAddressService by inject()

    post("/address") {
        clientWithBody<PostAddress, GetAddress> { bodyRequest ->
            addressService.createAddress(bodyRequest.request.jwtUser.uuid, bodyRequest.body)
        }
    }
}