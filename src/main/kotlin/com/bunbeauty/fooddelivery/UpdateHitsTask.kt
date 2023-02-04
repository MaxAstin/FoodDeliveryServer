package com.bunbeauty.fooddelivery

import java.util.*

fun startUpdateHitsTask() {
    Timer("Update hits").schedule(
        object : TimerTask() {
            override fun run() {
                println("Update hits task")
            }
        },
        0L,
        24 * 60 * 60 * 1000
    )
}