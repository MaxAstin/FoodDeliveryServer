package com.bunbeauty.fooddelivery

import java.util.*

fun startUpdateHitsTask() {
    Timer("Update hits").schedule(
        object : TimerTask() {
            override fun run() {
                println("Update hits task")
            }
        },
        1_000L,
        60_000L
    )
}