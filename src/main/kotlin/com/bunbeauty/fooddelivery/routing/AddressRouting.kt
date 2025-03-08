package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.CAFE_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.QUERY_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.address.AddressService
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.GetAddressV2
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddress
import com.bunbeauty.fooddelivery.domain.feature.address.model.PostAddressV2
import com.bunbeauty.fooddelivery.routing.extension.clientGetListResult
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureAddressRouting() {
    routing {
        patchAddressWithCafeUuid()

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
        clientGetListResult { request ->
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
        clientGetListResult { request ->
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
        clientGetListResult {
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

/*
* TODO(Remove in 1.0.5 release after add all cafeUuid to address)
* */
private fun Route.patchAddressWithCafeUuid() {
    val addressService: AddressService by inject()

    get("/patch_address_with_cafe") {
        clientGetListResult {
            val cityUuid = call.getParameter(CITY_UUID_PARAMETER)
            val cafeUuid = call.getParameter(CAFE_UUID_PARAMETER)

            addressService.addToAddressCafeUuid(
                cityUuid = cityUuid,
                cafeUuid = cafeUuid
            )
            emptyList()
        }
    }
}
