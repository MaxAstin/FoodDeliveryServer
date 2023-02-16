package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.service.new_statistic.NewStatisticService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Seconds
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate
import org.koin.ktor.ext.inject

fun Application.startUpdateStatisticTask() {

    val newStatisticService: NewStatisticService by inject()

    val now = DateTime.now()
    val secondsToTomorrow = Seconds.secondsBetween(
        now,
        now.withTimeAtStartOfDay().plusDays(1)
    ).seconds
    println("secondsToTomorrow $secondsToTomorrow")
    Timer("Update statistic").scheduleAtFixedRate(
        delay = 20_000,//secondsToTomorrow * 1_000L,
        period = TimeUnit.HOURS.toMillis(24),
    ) {
        launch {
            newStatisticService.updateStatistic()
        }
    }
}