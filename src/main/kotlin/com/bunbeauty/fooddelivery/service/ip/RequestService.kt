package com.bunbeauty.fooddelivery.service.ip

import com.bunbeauty.fooddelivery.data.Constants.DAY_REQUEST_LIMIT
import com.bunbeauty.fooddelivery.data.Constants.REQUIRED_TIME_BETWEEN_REQUESTS
import com.bunbeauty.fooddelivery.data.repo.request.IRequestRepository
import com.bunbeauty.fooddelivery.domain.error.ExceptionWithCode
import com.bunbeauty.fooddelivery.domain.error.errorWithCode
import com.bunbeauty.fooddelivery.domain.model.request.InsertRequest
import org.joda.time.DateTime

private const val TOO_MANY_REQUESTS_CODE = 800

class RequestService(private val requestRepository: IRequestRepository) {

    /**
     * Checks the availability of a request based on the IP address and request name.
     *
     * @param ip The IP address of the requester.
     * @param requestName The name of the request.
     *
     * @throws ExceptionWithCode indicating that there are too many requests.
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
        errorWithCode(
            message = message,
            code = TOO_MANY_REQUESTS_CODE
        )
    }
}
