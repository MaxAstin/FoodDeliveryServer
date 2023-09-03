package com.bunbeauty.fooddelivery.data.model.request

sealed class RequestAvailability {

    data object Available : RequestAvailability()
    class NotAvailable(val seconds: Int) : RequestAvailability()
}
