package com.bunbeauty.food_delivery.routing.model

data class BodyRequest<B>(
    val request: Request,
    val body: B,
)