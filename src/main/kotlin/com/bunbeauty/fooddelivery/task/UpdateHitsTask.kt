package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.service.hit.IHitService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate
import org.koin.ktor.ext.inject

fun Application.startUpdateHitsTask() {

    val hitService: IHitService by inject()

    Timer("Update hits").scheduleAtFixedRate(
        delay = 5_000L,
        period = TimeUnit.HOURS.toMillis(24),
    ) {
        launch {
            hitService.updateHits()
        }
    }
}