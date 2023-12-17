package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.QUERY_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.address.AddressService
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddressV2
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.getClientWithListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureAddressRouting() {

    routing {
        authenticate {
            getAddresses()
            postAddress()

            getSuggestions()
            getAddressesV2()
            postAddressV2()
        }
    }
}

private fun Route.getAddresses() {

    val addressService: AddressService by inject()

    get("/address") {
        getClientWithListResult { request ->
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            addressService.getAddressListByUserUuidAndCityUuid(
                userUuid = request.jwtUser.uuid,
                cityUuid = cityUuid
            )
        }
    }
}

private fun Route.getAddressesV2() {

    val addressService: AddressService by inject()

    get("/v2/address") {
        getClientWithListResult { request ->
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            addressService.getAddressListByUserUuidAndCityUuidV2(
                userUuid = request.jwtUser.uuid,
                cityUuid = cityUuid
            )
        }
    }
}

private fun Route.getSuggestions() {

    val addressService: AddressService by inject()

    get("/street/suggestions") {
        getClientWithListResult {
            val query = call.getParameter(QUERY_PARAMETER)
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            addressService.getStreetSuggestionList(
                query = query,
                cityUuid = cityUuid
            )
        }
    }
}

private fun Route.postAddress() {

    val addressService: AddressService by inject()

    post("/address") {
        clientWithBody<PostAddress, GetAddress> { bodyRequest ->
            addressService.createAddress(
                userUuid = bodyRequest.request.jwtUser.uuid,
                postAddress = bodyRequest.body
            )
        }
    }
}

private fun Route.postAddressV2() {

    val addressService: AddressService by inject()

    post("/v2/address") {
        clientWithBody<PostAddressV2, GetAddressV2> { bodyRequest ->
            addressService.createAddressV2(
                userUuid = bodyRequest.request.jwtUser.uuid,
                postAddress = bodyRequest.body
            )
        }
    }
}