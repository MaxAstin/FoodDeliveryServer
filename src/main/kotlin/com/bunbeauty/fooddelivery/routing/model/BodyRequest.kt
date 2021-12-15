package com.bunbeauty.fooddelivery.routing.model

data class BodyRequest<B>(
    val request: Request,
    val body: B,
)