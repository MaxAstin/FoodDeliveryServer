package com.bunbeauty.fooddelivery.domain.model.request

sealed class RequestAvailability {

    data object Available : RequestAvailability()
    class NotAvailable(val seconds: Int) : RequestAvailability()
}
