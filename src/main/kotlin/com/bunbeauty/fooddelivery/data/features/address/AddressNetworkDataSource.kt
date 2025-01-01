package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressByIdRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.SuggestionsResponse
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.network.postData
import io.ktor.client.HttpClient

class AddressNetworkDataSource(private val client: HttpClient) {

    suspend fun requestAddressSuggestions(addressRequestBody: AddressRequestBody): ApiResult<SuggestionsResponse> {
        return client.postData(
            path = "suggest/address",
            body = addressRequestBody
        )
    }

    suspend fun requestAddressSuggestionById(addressByIdRequestBody: AddressByIdRequestBody): ApiResult<SuggestionsResponse> {
        return client.postData(
            path = "findById/address",
            body = addressByIdRequestBody
        )
    }
}
