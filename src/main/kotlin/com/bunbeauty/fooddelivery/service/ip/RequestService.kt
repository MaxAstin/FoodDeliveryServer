package com.bunbeauty.fooddelivery.service.ip

import com.bunbeauty.fooddelivery.data.Constants.CONSECUTIVE_REQUESTS_LIMIT
import com.bunbeauty.fooddelivery.data.Constants.REQUEST_LIMIT_TIMEOUT
import com.bunbeauty.fooddelivery.data.model.request.InsertRequest
import com.bunbeauty.fooddelivery.data.model.request.RequestAvailability
import com.bunbeauty.fooddelivery.data.repo.request.IRequestRepository
import org.joda.time.DateTime

class RequestService(private val requestRepository: IRequestRepository) {

    /**
        Request ([requestName]) is available for concrete [ip] in 2 cases:
         - more than [REQUEST_LIMIT_TIMEOUT] milliseconds have passed since the last request
         - less than [CONSECUTIVE_REQUESTS_LIMIT] requests in a day
     */
    suspend fun isRequestAvailable(ip: String, requestName: String): RequestAvailability {
        val startDayMillis = DateTime.now().withTimeAtStartOfDay().millis
        val lastRequestTime = requestRepository.getLastDayRequestByIpAndName(ip, requestName, startDayMillis)?.time ?: 0L
        val currentTimeMillis = DateTime.now().millis
        val untilNextRequest = lastRequestTime + REQUEST_LIMIT_TIMEOUT - currentTimeMillis

        val dayRequestCount = requestRepository.getDayRequestCountByIpAndName(ip, requestName, startDayMillis)

        return (untilNextRequest < 0 || dayRequestCount < CONSECUTIVE_REQUESTS_LIMIT).let { isAvailable ->
            if (isAvailable) {
                requestRepository.insertRequest(
                    InsertRequest(
                        ip = ip,
                        name = requestName,
                        time = currentTimeMillis
                    )
                )

                RequestAvailability.Available
            } else {
                RequestAvailability.NotAvailable((untilNextRequest / 1000).toInt())
            }
        }
    }
}

