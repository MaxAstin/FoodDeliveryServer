package com.bunbeauty.fooddelivery.service.ip

import com.bunbeauty.fooddelivery.data.Constants.DAY_REQUEST_LIMIT
import com.bunbeauty.fooddelivery.data.Constants.REQUIRED_TIME_BETWEEN_REQUESTS
import com.bunbeauty.fooddelivery.data.model.request.InsertRequest
import com.bunbeauty.fooddelivery.data.repo.request.IRequestRepository
import com.bunbeauty.fooddelivery.error.errorWithCode
import org.joda.time.DateTime

class RequestService(private val requestRepository: IRequestRepository) {

    /**
        Params:
         - ip - IP address of user
         - requestName - name of specific request
        Throws:
         - exception if the time since the last request with the same [requestName] for the same [ip] was made less than [REQUIRED_TIME_BETWEEN_REQUESTS] ago
         - exception if the count of requests with the same [requestName] for the same [ip] was made equal to or more than [DAY_REQUEST_LIMIT] times
     */
    suspend fun checkRequestAvailability(ip: String, requestName: String) {
        println("isRequestAvailable requestName: $requestName ip: $ip")

        val currentTimeMillis = DateTime.now().millis
        val lastRequestTime = requestRepository.getLastRequestByIpAndName(ip, requestName)?.time ?: 0L
        if (currentTimeMillis - lastRequestTime < REQUIRED_TIME_BETWEEN_REQUESTS) {
            val untilNextRequestMillis = REQUIRED_TIME_BETWEEN_REQUESTS - (currentTimeMillis - lastRequestTime)
            val untilNextRequestSeconds = (untilNextRequestMillis / 1000).toInt()
            tooManyRequestsError(untilNextRequestSeconds)
        }

        val requestCount = requestRepository.getRequestCountByIpAndName(ip, requestName)
        if (requestCount >= DAY_REQUEST_LIMIT) {
            tooManyRequestsError()
        }

        requestRepository.insertRequest(
            InsertRequest(
                ip = ip,
                name = requestName,
                time = currentTimeMillis
            )
        )
    }

    suspend fun clearRequests() {
        requestRepository.deleteAll()
    }

    private fun tooManyRequestsError(seconds: Int? = null): Nothing {
        var message = "Too many requests. Please wait"
        if (seconds != null) {
            message += " $seconds s"
        }
        errorWithCode(message, 800)
    }
}

