package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.domain.feature.menu.service.HitService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

fun Application.scheduleUpdateHitsTask() {
    val hitService: HitService by inject()

    Timer("Update hits").scheduleAtFixedRate(
        delay = 5_000L,
        period = TimeUnit.HOURS.toMillis(24)
    ) {
        launch {
            hitService.updateHits()
        }
    }
}
