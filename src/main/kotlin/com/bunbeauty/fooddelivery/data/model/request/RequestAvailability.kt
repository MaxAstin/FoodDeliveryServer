package com.bunbeauty.fooddelivery.data.model.request

sealed class RequestAvailability {

    object Available : RequestAvailability()
    class NotAvailable(val seconds: Int) : RequestAvailability()
}
