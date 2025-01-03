package com.bunbeauty.fooddelivery.util

val Throwable.fullMessage: String
    get() {
        return buildString {
            appendLine("Extension: $message")
            var currentCause = cause
            while (currentCause != null) {
                appendLine("Caused by: ${currentCause.message}")
                currentCause = currentCause.cause
            }
        }
    }
