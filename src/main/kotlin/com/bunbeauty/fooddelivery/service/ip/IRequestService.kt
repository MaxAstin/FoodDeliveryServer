package com.bunbeauty.fooddelivery.service.ip

import com.bunbeauty.fooddelivery.data.model.request.RequestAvailability

interface IRequestService {

    suspend fun isRequestAvailable(ip: String, requestName: String): RequestAvailability
}