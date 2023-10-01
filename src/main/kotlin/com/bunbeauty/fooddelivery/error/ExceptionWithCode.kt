package com.bunbeauty.fooddelivery.error

class ExceptionWithCode(
    override val message: String,
    val code: Int,
): Exception(message)