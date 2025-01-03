package com.bunbeauty.fooddelivery.data.session

import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.atomic.AtomicInteger

class Session<T> {

    var count: AtomicInteger = AtomicInteger(1)
    val mutableSharedFlow = MutableSharedFlow<T>(replay = 0)
}
