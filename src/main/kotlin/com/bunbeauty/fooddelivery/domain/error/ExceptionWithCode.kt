package com.bunbeauty.fooddelivery.domain.error

class ExceptionWithCode(
    override val message: String,
    val code: Int
) : Exception(message)
