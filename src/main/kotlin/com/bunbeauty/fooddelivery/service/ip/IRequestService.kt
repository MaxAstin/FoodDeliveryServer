package com.bunbeauty.fooddelivery.service.ip

import com.bunbeauty.fooddelivery.domain.model.request.RequestAvailability

interface IRequestService {

    suspend fun isRequestAvailable(ip: String, requestName: String): RequestAvailability
}
