package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.service.ip.RequestService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Seconds
import org.koin.ktor.ext.inject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

fun Application.scheduleClearRequestsTask() {

    val requestService: RequestService by inject()

    val now = DateTime.now()
    val secondsToTomorrow = Seconds.secondsBetween(
        now,
        now.withTimeAtStartOfDay().plusDays(1)
    ).seconds
    Timer("Clear requests").scheduleAtFixedRate(
        delay = secondsToTomorrow * 1_000L,
        period = TimeUnit.HOURS.toMillis(24),
    ) {
        launch {
            requestService.clearRequests()
        }
    }
}