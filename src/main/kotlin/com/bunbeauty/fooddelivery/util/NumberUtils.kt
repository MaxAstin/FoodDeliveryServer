package com.bunbeauty.fooddelivery.util

fun Int?.orZero(): Int {
    return this ?: 0
}