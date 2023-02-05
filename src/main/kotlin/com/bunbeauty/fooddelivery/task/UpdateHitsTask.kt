package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.service.hit.IHitService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate
import org.koin.ktor.ext.inject

fun Application.startUpdateHitsTask() {

    val hitService: IHitService by inject()
    val updateHitsJob = launch {
        hitService.updateHits()
    }

    Timer("Update hits").scheduleAtFixedRate(
        delay = 5_000L,
        period = 5 * 60 * 1000,
    ) {
        updateHitsJob.start()
    }
}