package com.bunbeauty.fooddelivery.task

import com.bunbeauty.fooddelivery.domain.feature.address.AddressService
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.scheduleAtFixedRate

fun Application.scheduleUpdateStreetsTask() {

    val addressService: AddressService by inject()

    Timer("Update streets").scheduleAtFixedRate(
        delay = 10_000L,
        period = TimeUnit.HOURS.toMillis(24),
    ) {
        launch {
            addressService.updateStreets()
        }
    }
}