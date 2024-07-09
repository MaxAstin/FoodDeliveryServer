package com.bunbeauty.fooddelivery.task

import io.ktor.server.application.*

fun Application.scheduleTasks() {
    scheduleUpdateHitsTask()
    scheduleUpdateStatisticTask()
    scheduleClearRequestsTask()
}