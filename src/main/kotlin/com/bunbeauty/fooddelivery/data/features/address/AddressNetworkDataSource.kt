package com.bunbeauty.fooddelivery.data.features.address

import com.bunbeauty.fooddelivery.data.features.address.remotemodel.AddressRequestBody
import com.bunbeauty.fooddelivery.data.features.address.remotemodel.SuggestionsResponse
import com.bunbeauty.fooddelivery.network.ApiResult
import com.bunbeauty.fooddelivery.network.safeCall
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AddressNetworkDataSource(private val client: HttpClient) {

    suspend fun requestAddressSuggestions(addressRequestBody: AddressRequestBody): ApiResult<SuggestionsResponse> {
//        return client.postData(
//            path = "address",
//            body = addressRequestBody
//        )

        return safeCall {
            client.post {
                setBody(addressRequestBody)
                url {
                    path("address")
                }
            }
        }
    }

}