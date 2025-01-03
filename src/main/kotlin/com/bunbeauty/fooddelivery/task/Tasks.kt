package com.bunbeauty.fooddelivery.task

import io.ktor.server.application.Application

fun Application.scheduleTasks() {
    scheduleUpdateHitsTask()
    scheduleUpdateStatisticTask()
    scheduleClearRequestsTask()
}
